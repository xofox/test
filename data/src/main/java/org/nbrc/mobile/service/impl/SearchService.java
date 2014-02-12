package org.nbrc.mobile.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.nbrc.mobile.helper.GroupInfo;
import org.nbrc.mobile.helper.JobItem;
import org.nbrc.mobile.helper.Person;
import org.nbrc.mobile.helper.SearchResult;
import org.nbrc.mobile.so.PersonSearchObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SearchService {
	public SearchResult<JobItem> search(String key,String more,String bwt,int page,int pagesize){
		SearchResult<JobItem> ps = new SearchResult<JobItem>(page, pagesize);
		SolrServer server = new HttpSolrServer("http://10.74.8.244:9090/solr");
		SolrQuery params = new SolrQuery();
		
		//查询参数
		//岗位名称加权重^4
		
		if(!StringUtils.hasText(key)){
			key="*";
		}
		
		String q = "GANG_WEI_MC:("+key + ")^4 OR " + "DAN_WEI_MC:" + key + " OR " + "GANG_WEI_MS:" + key + " OR " + " DAN_WEI_JJ:" + key + "";
		if(StringUtils.hasText(more)){
			q = "(" + q + ") AND " + more;
		}
		
		if(StringUtils.hasText(bwt)){
			q = "(" + q + ")" + "AND FA_BU_SJ:[NOW/DAY-" + bwt+"DAY TO NOW/DAY+1DAY]";
		}
		//日期搜索格式如下:
		//pubdate:[NOW-1YEAR/DAY TO NOW/DAY+1DAY]
		//createdate:[1976-03-06T23:59:59.999Z TO *]
		//createdate:[1995-12-31T23:59:59.999Z TO 2007-03-06T00:00:00Z]
		
		//System.out.println(q);
		params.setQuery(q);
		
		//高亮
		params.setHighlight(true);
		params.setHighlightSimplePre("<font color='red'>");
		params.setHighlightSimplePost("</font>");
		params.addHighlightField("GANG_WEI_MC")
			.addHighlightField("DAN_WEI_MC")
			.addHighlightField("GANG_WEI_MS")
			.addHighlightField("DAN_WEI_JJ");
		
		//facet		
		params.setFacet(true);
		params.addFacetField("GONG_ZUO_DD","GWLB1");
		params.setFacetLimit(10);
		
		//sorting
		params.addSortField("score", ORDER.desc); //得分越高，关键词越相近
		params.addSortField("XIU_GAI_SJ", ORDER.desc);
		
		
		//pageing
		params.setStart(ps.getStart());
		params.setRows(ps.getPagesize());
		
		SolrDocumentList docs= null;
		Map<String,Map<String,List<String>>> high= null;
		List<FacetField> facets = null;
		
		try{
			QueryResponse response = server.query(params);
			docs = response.getResults();
			high = response.getHighlighting();
			facets = response.getFacetFields();			
		}catch(Exception e){
			e.printStackTrace();
		}
		List<JobItem> items = new ArrayList<JobItem>();
		for(SolrDocument doc:docs){
			JobItem item = new JobItem();
			String jid =doc.get("GANG_WEI_BH").toString();
			item.setjId(jid); //主键
			Map<String,List<String>> hh = high.get(jid);
			item.setBussiness(doc.get("HANG_YE_LB")!=null?"":doc.get("HANG_YE_LB").toString());
			item.setCateId1(doc.get("GWLB1")==null?"":doc.get("GWLB1").toString());
			item.setCateId2(doc.get("GWLB12")==null?"":doc.get("GWLB12").toString());
			item.setCateId3(doc.get("GWLB13")==null?"":doc.get("GWLB13").toString());
			item.setCateIds(doc.get("GANG_WEI_LB")==null?"":doc.get("GANG_WEI_LB").toString());
			if(hh.containsKey("DAN_WEI_JJ")){
				item.setCompDescription(hh.get("DAN_WEI_JJ").toString());
			}else{
				item.setCompDescription(doc.get("DAN_WEI_JJ")==null?"":doc.get("DAN_WEI_JJ").toString());
			}
			item.setCompId(doc.get("COMPID").toString());
			
			if(hh.containsKey("DAN_WEI_MC")){
				item.setCompName(hh.get("DAN_WEI_MC").toString());
			}else{
				item.setCompName(doc.get("DAN_WEI_MC")==null?"":doc.get("DAN_WEI_MC").toString());
			}
			
			item.setEduLevel(doc.get("XLMC")==null?"":doc.get("XLMC").toString());
			item.setEndAt(doc.get("JIE_ZHI_RQ")==null?null:(Date)doc.getFieldValue("JIE_ZHI_RQ"));
			item.setGender(doc.get("XING_BIE")==null?"":doc.get("XING_BIE").toString());
			if(hh.containsKey("GANG_WEI_MS")){
				item.setJobDescription(hh.get("GANG_WEI_MS").toString());
			}else{
				item.setJobDescription(doc.get("GANG_WEI_MS")==null?"":doc.get("GANG_WEI_MS").toString());
			}
			if(hh.containsKey("GANG_WEI_MC")){
				item.setJobName(hh.get("GANG_WEI_MC").toString());
			}else{
				item.setJobName(doc.get("GANG_WEI_MC")==null?"":doc.get("GANG_WEI_MC").toString());
			}
			
			item.setModAt(doc.get("XIU_GAI_SJ")==null?null:(Date)doc.getFieldValue("XIU_GAI_SJ"));
			item.setPart(doc.get("SFJZ")==null?"":doc.get("SFJZ").toString());
			item.setPubAt(doc.get("FA_BU_SJ")==null?null:(Date)doc.getFieldValue("FA_BU_SJ"));
			item.setSalary(doc.get("ZUI_DI_XL")==null?"":doc.get("ZUI_DI_XL").toString());
			item.setWorkAt(doc.get("GONG_ZUO_DD")==null?"":doc.get("GONG_ZUO_DD").toString());
			item.setWorkAtCh(doc.get("GONG_ZU_DD_ZW")==null?"":doc.get("GONG_ZU_DD_ZW").toString());
			item.setWorkYear(doc.get("GONG_LING")==null?"":doc.get("GONG_LING").toString());
			items.add(item);
		}
		
		Map<String,List<GroupInfo>> groups = new LinkedHashMap<String,List<GroupInfo>>();
		
		for(FacetField ff:facets){
			String ky = ff.getName();
			List<GroupInfo> lg = new ArrayList<GroupInfo>();
			if(ff.getValueCount()>0){
				for(Count cn : ff.getValues()){
					GroupInfo gi = new GroupInfo();
					gi.setName(cn.getName());
					gi.setCount(cn.getCount());
					lg.add(gi);
				}
			}
			groups.put(ky, lg);
		}
		ps.setGroups(groups);
		ps.setItems(items);
		ps.setTotal((int)docs.getNumFound());
		return ps;
	}

	public SearchResult<Person> queryPerson(PersonSearchObject pso, int page,int pagesize) {
		SearchResult<Person> result = new SearchResult<Person>(page, pagesize);
		SolrServer server = new HttpSolrServer("http://10.74.8.244:7070/solr");
		SolrQuery params = new SolrQuery();
		String q = pso.makeQueryString();
		params.setQuery(q);
		//System.out.println(q);
		
		params.setHighlight(true);
		params.setHighlightSimplePre("<font color=\"red\">");
		params.setHighlightSimplePost("</font>");
		params.addHighlightField("ZYMC").addHighlightField("BZ");
		
		params.setFacet(true);
		params.addFacetField("XLBH","GL");
		params.setFacetLimit(10);
		
		params.addSortField("score", ORDER.desc);
		params.addSortField("ZHSXSJ", ORDER.desc);
		
		params.setStart(result.getStart());
		params.setRows(result.getPagesize());
		
		SolrDocumentList list  = null;
		Map<String,Map<String,List<String>>> high = null;
		List<FacetField> facets  = null;
		try {
			QueryResponse res = server.query(params);
			list = res.getResults();
			high = res.getHighlighting();
			facets = res.getFacetFields();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Person> persons = new LinkedList<Person>();
		for(SolrDocument doc:list){
			Person p = new Person();
			String pid = doc.get("PERSONID").toString();
			p.setPersonId(pid);
			Map<String,List<String>> hh = high.get(pid);
			
			p.setBirthday(doc.get("CSRQ")==null?null:(Date) doc.get("CSRQ"));
			p.setEdulevel(doc.get("XLBH")==null?"":doc.get("XLBH").toString());
			p.setGender(doc.get("XB")==null?"":doc.get("XB").toString());
			p.setHigh(doc.get("HH")==null?"":doc.get("HH").toString());
			p.setHome(doc.get("HKS")==null?"":doc.get("HKS").toString());
			p.setIndendBn(doc.get("ZC2")==null?"":doc.get("ZC2").toString());
			p.setIntendA(doc.get("QZYXA")==null?"":doc.get("QZYXA").toString());
			p.setIntendAn(doc.get("ZC1")==null?"":doc.get("ZC1").toString());
			p.setIntendB(doc.get("QZYXB")==null?"":doc.get("QZYXB").toString());
			p.setIntendC(doc.get("QZYXC")==null?"":doc.get("QZYXC").toString());
			p.setIntendCn(doc.get("ZC3")==null?"":doc.get("ZC3").toString());
			if(hh.containsKey("BZ")){
				p.setMemo(hh.get("BZ").toString());
			}else{
				p.setMemo(doc.get("BZ")==null?"":doc.get("BZ").toString());
			}			
			p.setModAt(doc.get("UPDTIME")==null?null:(Date)doc.get("UPDTIME"));
			p.setName(doc.get("XM")==null?"":doc.get("XM").toString());
			p.setNowWork(doc.get("XGZCS")==null?"":doc.get("XGZCS").toString());
			p.setNowWorkCity(doc.get("XZQMC")==null?"":doc.get("XZQMC").toString());
			if(hh.containsKey("ZYMC")){
				p.setProName(hh.get("ZYMC").toString());
			}else{
				p.setProName(doc.get("ZYMC")==null?"":doc.get("ZYMC").toString());
			}
			p.setRefreshAt(doc.get("ZHSXSJ")==null?null:(Date) doc.get("ZHSXSJ"));
			p.setRegAt(doc.get("INSTIME")==null?null:(Date) doc.get("INSTIME"));
			p.setrId(doc.get("JLBH")==null?"":doc.get("JLBH").toString());
			p.setTrs(doc.get("TRS")==null?"":doc.get("TRS").toString());
			persons.add(p);
		}
		
		Map<String,List<GroupInfo>> groups = new LinkedHashMap<String,List<GroupInfo>>();
		
		for(FacetField ff:facets){
			String ky = ff.getName();
			List<GroupInfo> lg = new ArrayList<GroupInfo>();
			if(ff.getValueCount()>0){
				for(Count cn : ff.getValues()){
					GroupInfo gi = new GroupInfo();
					gi.setName(cn.getName());gi.setCount(cn.getCount());
					lg.add(gi);
				}
			}
			groups.put(ky, lg);
		}
		
		result.setGroups(groups);
		result.setItems(persons);
		result.setTotal((int)list.getNumFound());
		return result;
	}
}

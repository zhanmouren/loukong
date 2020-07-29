package com.koron.inwlms.service.report.waterReport.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.common.web.mapper.LongTreeBean;
import com.koron.common.web.mapper.TreeMapper;
import com.koron.inwlms.bean.DTO.indexData.IndicatorNewDTO;
import com.koron.inwlms.bean.DTO.report.waterBalanceReport.WB1BalanceDTO;
import com.koron.inwlms.bean.VO.common.IndicatorVO;
import com.koron.inwlms.bean.VO.common.PageVO;
import com.koron.inwlms.bean.VO.indexData.TreeZoneVO;
import com.koron.inwlms.bean.VO.report.waterBalanceReport.TreefirstVO;
import com.koron.inwlms.bean.VO.report.waterBalanceReport.WB1BalanceVO;
import com.koron.inwlms.bean.VO.report.waterBalanceReport.WB2OneZoneVO;
import com.koron.inwlms.mapper.report.waterReport.WaterReportMapper;
import com.koron.inwlms.mapper.sysManager.UserMapper;
import com.koron.inwlms.service.report.waterReport.WaterReportService;
import com.koron.inwlms.util.PageUtil;
import com.koron.util.Constant;


@Service
public  class WaterReportServiceImpl implements WaterReportService{
	//时间类型（年，月，日）
	public final Integer timeType=1;
	//树的类型
	public final Integer treeType=2;
	
	public final Integer parentmask=0;

	//(WB_01)水司及一级分区产销差率同比报表      以月为时间间隔,汇总分析所选运作区或全网在指定时间范围内用水量、产销差和未计量食水用水量。
	@TaskAnnotation("queryPartitionData")
	@Override
	public Map<String,Object> queryPartitionData(SessionFactory factory, IndicatorNewDTO indicatorNewDTO) {
		WaterReportMapper waterReportMapper = factory.getMapper(WaterReportMapper.class);
		/**
		  * BALANCE_INDIC  水平衡基础数据---  计费计量用水量1    免费计量用水量    供水（总）量 1      
		  * 							---  未计量食水用水量  产销差量1 用于计算同比
		  * ZONE_LOSS_INDIC 分区漏损数据   --- 未计量用水量占比  产销差率1
		  * 
		  */
		//最终返回的数据
		List<WB1BalanceVO> finalWB1BalanceList=new ArrayList<>();
		
	     DecimalFormat df = new DecimalFormat("0.00%");
		
		
		
		List<String>  monthList=new ArrayList<>();
		try {
			//获取中间月份
		   monthList=getMonthBetween(indicatorNewDTO.getStartTime().toString(),indicatorNewDTO.getEndTime().toString());
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
		
	    for(int i=0;i<monthList.size();i++) {	 
	    	WB1BalanceVO wB1BalanceVO=new WB1BalanceVO();
	    	wB1BalanceVO.setMonthId(monthList.get(i));
	    	indicatorNewDTO.setStartTime(Integer.valueOf(monthList.get(i)));
	    	indicatorNewDTO.setEndTime(Integer.valueOf(monthList.get(i)));
	    	indicatorNewDTO.setTimeType(timeType);
	    	
	    	 Integer startTime=indicatorNewDTO.getStartTime();
	    	 Integer endTime=indicatorNewDTO.getEndTime();
	    	 Integer lastStartYear=Integer.valueOf(indicatorNewDTO.getStartTime().toString().substring(0, 4))-1;
			 //去年开始时间本月
			 Integer lastStartTime=Integer.valueOf(lastStartYear.toString()+indicatorNewDTO.getStartTime().toString().substring(4, 6));
			 Integer lastEndYear=Integer.valueOf(indicatorNewDTO.getEndTime().toString().substring(0, 4))-1;
			 //去年结束时间本月
			 Integer lastEndTime=Integer.valueOf(lastEndYear.toString()+indicatorNewDTO.getEndTime().toString().substring(4, 6));
			 
			//查询水平衡基础数据(本月)
			List<String> balanceCurCodes=new ArrayList<>();
			//如果是全网 全网可以直接所有的算出来
			if("1".equals(indicatorNewDTO.getZoneCodes().get(0))) {
				//计费计量用水量
				balanceCurCodes.add(Constant.BALANCE_INDIC_WNMBMC);
				// 免费计量用水量 
				balanceCurCodes.add(Constant.BALANCE_INDIC_WNMUMC);
				//供水（总）量
				balanceCurCodes.add(Constant.BALANCE_INDIC_WNMFWSSITDF);
				//未计量食水用水量
				balanceCurCodes.add(Constant.BALANCE_INDIC_WNMUFWC);
				// 产销差量
				balanceCurCodes.add(Constant.BALANCE_INDIC_WNMNRW);
				//未计量用水量占比
				balanceCurCodes.add(Constant.ZONE_LOSS_INDIC_WNMUCRFW);
				//产销差率
				balanceCurCodes.add(Constant.ZONE_LOSS_INDIC_WNMNRR);
				
				 indicatorNewDTO.setCodes(balanceCurCodes);
				 //计算本年本月的
				 List<IndicatorVO> balancecurList=waterReportMapper.queryCompanyIndicData(indicatorNewDTO);
				 
				 if(balancecurList!=null && balancecurList.size()>0) {
					 //开始赋值
					 for(int y=0;y<balancecurList.size();y++) {
						 if(balancecurList.get(y).getCode().contains(Constant.BALANCE_INDIC_WNMBMC)) {
							 wB1BalanceVO.setBilledMeteredConsumption(new BigDecimal(balancecurList.get(y).getValue()/10000).setScale(2, RoundingMode.UP).doubleValue());
							 continue;
						 }
						 if(balancecurList.get(y).getCode().contains(Constant.BALANCE_INDIC_WNMUMC)) {
							 wB1BalanceVO.setUnbilledMeteredConsumption(new BigDecimal(balancecurList.get(y).getValue()/10000).setScale(2, RoundingMode.UP).doubleValue());
							 continue;
						 }
						 if(balancecurList.get(y).getCode().contains(Constant.BALANCE_INDIC_WNMFWSSITDF)) {
							 wB1BalanceVO.setSystemInput(new BigDecimal(balancecurList.get(y).getValue()/10000).setScale(2, RoundingMode.UP).doubleValue());
							 continue;
						 }
						 //未计量用水量占比
						 if(balancecurList.get(y).getCode().contains(Constant.ZONE_LOSS_INDIC_WNMUCRFW)) {
							 wB1BalanceVO.setCurrentW(balancecurList.get(y).getValue()*100);
							 continue;
						 }
						 //产销差率
						 if(balancecurList.get(y).getCode().contains(Constant.ZONE_LOSS_INDIC_WNMNRR)) {
							 wB1BalanceVO.setCurrentC(balancecurList.get(y).getValue()*100);
							 continue;
						 }
						//产销量本月
						 if(balancecurList.get(y).getCode().contains(Constant.BALANCE_INDIC_WNMNRW)) {
							 wB1BalanceVO.setCxcNow(balancecurList.get(y).getValue());
							 continue;
						 }
						//未计量食水用水量本月
						 if(balancecurList.get(y).getCode().contains(Constant.BALANCE_INDIC_WNMUFWC)) {
							 wB1BalanceVO.setWjlNow(balancecurList.get(y).getValue());
							 continue;
						 }
					 }
					 //计算total				
					 wB1BalanceVO.setTotal(new BigDecimal(wB1BalanceVO.getBilledMeteredConsumption()+wB1BalanceVO.getUnbilledMeteredConsumption()).setScale(2, RoundingMode.UP).doubleValue()); 				 
					 		
					 
				 }
				 //计算去年本月的
				 //未计量食水用水量
				 balanceCurCodes.add(Constant.BALANCE_INDIC_WNMUFWC);
					// 产销差量
				 balanceCurCodes.add(Constant.BALANCE_INDIC_WNMNRW);
				indicatorNewDTO.setCodes(balanceCurCodes);
				 indicatorNewDTO.setStartTime(lastStartTime);
				 indicatorNewDTO.setEndTime(lastEndTime);
				 List<IndicatorVO> balanceLastList=waterReportMapper.queryCompanyIndicData(indicatorNewDTO);
				 if(balanceLastList!=null && balanceLastList.size()>0) {
					for(int y=0;y<balanceLastList.size();y++) {
						 //未计量用水量占比
						 if(balanceLastList.get(y).getCode().contains(Constant.BALANCE_INDIC_WNMNRW)) {
							 wB1BalanceVO.setCurrentW(balanceLastList.get(y).getValue()*100);
							 continue;
						 }
						 //产销差率
						 if(balanceLastList.get(y).getCode().contains(Constant.ZONE_LOSS_INDIC_WNMNRR)) {
							 wB1BalanceVO.setCurrentC(balanceLastList.get(y).getValue()*100);
							 continue;
						 }
						//产销量本月
						 if(balanceLastList.get(y).getCode().contains(Constant.BALANCE_INDIC_WNMNRW)) {
							 wB1BalanceVO.setCxcNow(balanceLastList.get(y).getValue());
							 continue;
						 }
						//未计量食水用水量本月
						 if(balanceLastList.get(y).getCode().contains(Constant.BALANCE_INDIC_WNMUFWC)) {
							 wB1BalanceVO.setWjlNow(balanceLastList.get(y).getValue());
							 continue;
						 }
					}
				 }
				 
				 //计算未计量同比
				 if(wB1BalanceVO.getWjlLast()==0.0) {
					 wB1BalanceVO.setYearAgoW(0.0);
				 }else {
					 float AgoW=(float)(wB1BalanceVO.getWjlNow()-wB1BalanceVO.getWjlLast()/wB1BalanceVO.getWjlLast());
					 double yearAgow=Math.ceil(AgoW*10000)/100;
					 wB1BalanceVO.setYearAgoW(yearAgow);
				 }
				 //计算产销差率同比
				 if(wB1BalanceVO.getCxcLast()==0.0) {
					 wB1BalanceVO.setYearAgoC(0.0);
				 }else {
					 float AgoC=(float)(wB1BalanceVO.getCxcNow()-wB1BalanceVO.getCxcLast()/wB1BalanceVO.getWjlLast());
					 double yearAgoC=Math.ceil(AgoC*10000)/100;
					 wB1BalanceVO.setYearAgoC(yearAgoC);
				 }
				 
										
			}else {
				//一级分区
				balanceCurCodes.add(Constant.BALANCE_INDIC_FLMBMC);
				balanceCurCodes.add(Constant.BALANCE_INDIC_FLMUMC);
				balanceCurCodes.add(Constant.BALANCE_INDIC_FLMFWSSITDF);
				balanceCurCodes.add(Constant.BALANCE_INDIC_FLMUFWC);
				balanceCurCodes.add(Constant.BALANCE_INDIC_FLMNRW);
				
				indicatorNewDTO.setCodes(balanceCurCodes);	
		    	//查询水平衡基础数据
				List<IndicatorVO> balanceCurList=waterReportMapper.queryWBBaseIndicData(indicatorNewDTO);
				
			    if(balanceCurList!=null && balanceCurList.size()>0) {
				  for(int y=0;y<balanceCurList.size();y++) {			
						//一级分区
						//计费计量用水量
						if(Constant.BALANCE_INDIC_FLMBMC.equals(balanceCurList.get(y).getCode())) {										
							wB1BalanceVO.setBilledMeteredConsumption(new BigDecimal(balanceCurList.get(y).getValue()/10000).setScale(2, RoundingMode.UP).doubleValue());
							continue;			
						}
						//免费计量用水量
						 if(Constant.BALANCE_INDIC_FLMUMC.equals(balanceCurList.get(y).getCode())){	
							 wB1BalanceVO.setUnbilledMeteredConsumption(new BigDecimal(balanceCurList.get(y).getValue()/10000).setScale(2, RoundingMode.UP).doubleValue());
							 continue;						
						}
						 //供水（总）量
						if(Constant.BALANCE_INDIC_FLMFWSSITDF.equals(balanceCurList.get(y).getCode())){						
							 wB1BalanceVO.setSystemInput(new BigDecimal(balanceCurList.get(y).getValue()/10000).setScale(2, RoundingMode.UP).doubleValue());
							continue;						
					   }
						//产销量本月
						 if(balanceCurList.get(y).getCode().contains(Constant.BALANCE_INDIC_FLMNRW)) {
							 wB1BalanceVO.setCxcNow(new BigDecimal(balanceCurList.get(y).getValue()/10000).setScale(2, RoundingMode.UP).doubleValue());
							 continue;
						 }
						//未计量食水用水量本月
						 if(balanceCurList.get(y).getCode().contains(Constant.BALANCE_INDIC_FLMUFWC)) {
							 wB1BalanceVO.setWjlNow(new BigDecimal(balanceCurList.get(y).getValue()/10000).setScale(2, RoundingMode.UP).doubleValue());
							 continue;
						 }
				  }
			  }
			 
			
			  //查询水平衡基础数据(去年今月)
		  	List<String> balanceLastCodes=new ArrayList<>();		     	
		  			//一级分区
		  	 balanceLastCodes.add(Constant.BALANCE_INDIC_FLMUFWC);
		  	 balanceLastCodes.add(Constant.BALANCE_INDIC_FLMNRW);	
		     indicatorNewDTO.setStartTime(lastStartTime);
		     indicatorNewDTO.setEndTime(lastEndTime);
			 indicatorNewDTO.setCodes(balanceLastCodes);
			 //查询水平衡基础数据(去年今月)
		  	 List<IndicatorVO> balanceLastList=waterReportMapper.queryWBBaseIndicData(indicatorNewDTO);
		  	 
		  	 
		  	//查询分区漏损数据(本月)
			List<String> zoneListCurCodes=new ArrayList<>();
			
			//本月 未计量用水量占比 
			zoneListCurCodes.add(Constant.ZONE_LOSS_INDIC_FLMUCRFW);
			//本月  产销差率
			zoneListCurCodes.add(Constant.ZONE_LOSS_INDIC_FLMNRR);
			 indicatorNewDTO.setStartTime(startTime);
		     indicatorNewDTO.setEndTime(endTime);
				
				indicatorNewDTO.setCodes(zoneListCurCodes);
				//查询分区漏损数据(本月)
			     List<IndicatorVO> zoneListCur=waterReportMapper.queryZoneLossIndicData(indicatorNewDTO);
			     
			     for(int y=0;y<zoneListCur.size();y++) {																	
					 //一级分区	
					//未计量占比
					if(Constant.ZONE_LOSS_INDIC_FLMUCRFW.equals(zoneListCur.get(y).getCode())) {							
							 wB1BalanceVO.setCurrentW(Math.ceil(zoneListCur.get(y).getValue()*10000)/100);
							 continue;
					 }
					//产销差率
					else {			
						wB1BalanceVO.setCurrentC(Math.ceil(zoneListCur.get(y).getValue()*10000)/100);
						continue;
					 }
													
			     }
			     
			     
			   //查询分区漏损数据(去年本月)
				 List<String> zoneListLastCodes=new ArrayList<>();		
				 indicatorNewDTO.setStartTime(lastStartTime);
				 indicatorNewDTO.setEndTime(lastEndTime);
						
				//去年本月 未计量用水量占比 
				zoneListLastCodes.add(Constant.ZONE_LOSS_INDIC_FLMUCRFW);
				//去年本月  产销差率
				zoneListLastCodes.add(Constant.ZONE_LOSS_INDIC_FLMNRR);
				
				indicatorNewDTO.setCodes(zoneListLastCodes);
				//查询分区漏损数据(去年本月)
			     List<IndicatorVO> zoneListLast=waterReportMapper.queryZoneLossIndicData(indicatorNewDTO);
			     
			     
			     for(int y=0;y<zoneListLast.size();y++) {		    														
						//未计量占比 上年本月
						if(Constant.ZONE_LOSS_INDIC_FLMUCRFW.equals(zoneListLast.get(y).getCode())) {								
							  wB1BalanceVO.setLastW(Math.ceil(zoneListLast.get(y).getValue()*10000)/100);
								continue;
						 }
						//产销差率 上年本月
						else {								
							   wB1BalanceVO.setLastC(Math.ceil(zoneListLast.get(y).getValue()*10000)/100);
							   continue;
						}													
									 
		       }
			     
			   //计算total				
				wB1BalanceVO.setTotal(new BigDecimal(wB1BalanceVO.getBilledMeteredConsumption()+wB1BalanceVO.getUnbilledMeteredConsumption()).setScale(2, RoundingMode.UP).doubleValue()); 
			     
			   //计算未计量同比
				 if(wB1BalanceVO.getWjlLast()==0.0) {
					 wB1BalanceVO.setYearAgoW(0.0);
				 }else {
					 float AgoW=(float)(wB1BalanceVO.getWjlNow()-wB1BalanceVO.getWjlLast()/wB1BalanceVO.getWjlLast());
					 double yearAgow=Math.ceil(AgoW*10000)/100;
					 wB1BalanceVO.setYearAgoW(yearAgow);
				 }
				 //计算产销差率同比
				 if(wB1BalanceVO.getCxcLast()==0.0) {
					 wB1BalanceVO.setYearAgoC(0.0);
				 }else {
					 float AgoC=(float)(wB1BalanceVO.getCxcNow()-wB1BalanceVO.getCxcLast()/wB1BalanceVO.getWjlLast());
					 double yearAgoC=Math.ceil(AgoC*10000)/100;
					 wB1BalanceVO.setYearAgoC(yearAgoC);
				 }
			     
			}
						
			//end
		     finalWB1BalanceList.add(wB1BalanceVO);	    	    		  	     	     
	    }
	    //分页 action
	      Map<String,Object> finalMap=new HashMap<>();
	      
	      finalMap.put("rowNumber", finalWB1BalanceList.size());
		 
		  int startIndex=(indicatorNewDTO.getPage()-1)*indicatorNewDTO.getPageCount();
		  int listsize=finalWB1BalanceList.size();
		  
		  //新建一个空的List集合接收它  	
		  List<WB1BalanceVO> copyList=new ArrayList<>();
		  if(Math.floor(listsize/indicatorNewDTO.getPageCount())>=indicatorNewDTO.getPage()) {
			  listsize=startIndex+indicatorNewDTO.getPageCount();
		  }
		  for(int n=startIndex;n<listsize;n++) {
			  copyList.add(finalWB1BalanceList.get(n));
		  }
		 // 插入分页信息
		  PageVO pageVO = PageUtil.getPageBean(indicatorNewDTO.getPage(), indicatorNewDTO.getPageCount(), listsize);
		  finalMap.put("pageCount", indicatorNewDTO.getPageCount());
		  finalMap.put("page", indicatorNewDTO.getPage());
		  finalMap.put("totalPage", pageVO.getRowNumber());
		  
	    //end
	    
	    //计算total 那行	
	     WB1BalanceVO totalVO=new WB1BalanceVO();
	     totalVO.setMonthId("total");
	     //定义供水量实际有值的个数
	     int gslNum=0;
	     //定义供水量的总值
	     double gslCount=0.0;
	     
	     //定义计费计量用水量个数
	     int jfjlNum=0;
	     //定义计费计量用水量总值
	     double jfjlCount=0.0;
	     
	     //免费计量用水量个数
	     int mfjlNum=0;
	     //定义免费计量用水量总值
	     double mfjlCount=0.0;
	     
	     //合计个数
	     int totalNum=0;
	     //合计总值
	     double totalCount=0.0;
	     
	     //本月未计量占比个数
	     int wjlzbNNum=0;
	     //本月未计量占比总值
	     double wjlzbNCount=0.0;
	     
	     //去年同期未计量个数
	     int wjlzbCNum=0;
	     //去年同期未计量总值
	     double wjlzbCCount=0.0;
	     
	     //未计量占比同比个数
	     int wtbNum=0;
	     //未计量占比同比总值
	     double wtbCount=0.0;
	     
	     //本月产销差率个数
	     int cxcNum=0;
	     //本月产销差总值
	     double cxcCount=0.0;
	     
	     //去年产销差率同期个数
	     int cxcLNum=0;
	     //去年产销差率总值
	     double cxcLCount=0.0;
	    
	     //产销差同比
	     int cxctbNum=0;
	     double cxctbCount=0.0;
	     
	     
	     for(int p=0;p<copyList.size();p++) {
	    	 if(copyList.get(p).getSystemInput()!=0.0) {
	    		 gslNum++;
	    		 gslCount+=copyList.get(p).getSystemInput();
	    	 }
	    	 if(copyList.get(p).getBilledMeteredConsumption()!=0.0) {
	    		 jfjlNum++;
	    		 jfjlCount+=copyList.get(p).getBilledMeteredConsumption();
	    	 }
	    	 if(copyList.get(p).getUnbilledMeteredConsumption()!=0.0) {
	    		 mfjlNum++;
	    		 mfjlCount+=copyList.get(p).getUnbilledMeteredConsumption();
	    	 }
	    	 if(copyList.get(p).getTotal()!=0.0) {
	    		 totalNum++;
	    		 totalCount+=copyList.get(p).getTotal();
	    	 }
	    	 if(copyList.get(p).getCurrentW()!=0.0) {
	    		 wjlzbNNum++;
	    		 wjlzbNCount+=copyList.get(p).getCurrentW();
	    	 }
	    	 if(copyList.get(p).getLastW()!=0.0) {
	    		 wjlzbCNum++;
	    		 wjlzbCCount+=copyList.get(p).getLastW(); 
	    	 }
	    	 if(copyList.get(p).getYearAgoW()!=0.0) {
	    		 wtbNum++;
	    		 wtbCount+=copyList.get(p).getYearAgoW();
	    	 }
	    	 if(copyList.get(p).getCurrentC()!=0.0) {
	    		 cxcNum++;
	    		 cxcCount+=copyList.get(p).getCurrentC();
	    	 }
	    	 if(copyList.get(p).getYearAgoC()!=0.0) {
	    		 cxctbNum++;
	    		 cxctbCount+=copyList.get(p).getYearAgoC();
	    	 }
	    	 	    	 
	     }
	     if(totalNum!=0.0) {
	        totalVO.setSystemInput(new BigDecimal((float)gslCount/gslNum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
	     }else {
	        totalVO.setSystemInput(0.0);
	     }
	     if(jfjlNum!=0.0) {
	         totalVO.setBilledMeteredConsumption(new BigDecimal((float)jfjlCount/jfjlNum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
	     }else {
	    	 totalVO.setBilledMeteredConsumption(0.0);
	     }
	     if(mfjlNum!=0.0) {
	        totalVO.setUnbilledMeteredConsumption(new BigDecimal((float)mfjlCount/mfjlNum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
	     }else {
	    	totalVO.setUnbilledMeteredConsumption(0.0);
	     }
	     if(totalNum!=0.0) {
	        totalVO.setTotal(new BigDecimal((float)totalCount/totalNum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
	     }else {
	    	 totalVO.setTotal(0.0); 
	     }
	     if(wjlzbNNum!=0) {
	        totalVO.setCurrentW(new BigDecimal((float)wjlzbNCount/wjlzbNNum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
	     }else {
	    	totalVO.setCurrentW(0.0);
	     }
	     if(wjlzbCNum!=0) {
	        totalVO.setLastW(new BigDecimal((float)wjlzbCCount/wjlzbCNum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
	     }else {
	    	 totalVO.setLastW(0.0); 
	     }
	     if(wtbNum!=0) {
	    	 totalVO.setYearAgoW(new BigDecimal((float)wtbCount/wtbNum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()); 
	     }else {
	    	 totalVO.setYearAgoW(0.0); 
	     }
	     if(cxcNum!=0) {
	    	 totalVO.setCurrentC(new BigDecimal((float)cxcCount/cxcNum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()); 
	     }else {
	    	 totalVO.setCurrentC(0.0); 
	     }
	     if(cxcLNum!=0) {
	    	 totalVO.setLastC(new BigDecimal((float)cxcLCount/cxcLNum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()); 
	     }else {
	    	 totalVO.setLastC(0.0); 
	     }
	     if(cxctbNum!=0) {
	    	 totalVO.setYearAgoC(new BigDecimal((float)cxctbCount/cxctbNum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()); 
	     }else {
	    	 totalVO.setYearAgoC(0.0); 
	     }
	     
	     copyList.add(totalVO);
	     finalMap.put("dataList",copyList);
		// return copyList;
	     
	     return finalMap;
	
	}
	
	/**
	 * 获取两个月份所有月份
	 * @param minDate 格式yyyy-mm
	 * @param maxDate
	 * @return
	 * @throws Exception
	 */
	 public static List<String> getMonthBetween(String minDate, String maxDate) throws Exception {
	        ArrayList<String> result = new ArrayList<String>();
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");//格式化为年月
	        Calendar min = Calendar.getInstance();
	        Calendar max = Calendar.getInstance();
	        min.setTime(sdf.parse(minDate));
	        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
	        max.setTime(sdf.parse(maxDate));
	        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
	        Calendar curr = min;
	        while (curr.before(max)) {
	            result.add(sdf.format(curr.getTime()));
	            curr.add(Calendar.MONTH, 1);
	        }
	        return result;
	    }

	 /**
	   * 查询一级分区
	  */
    @TaskAnnotation("queryTreeOneZone")
	@Override
	public List<TreeZoneVO> queryTreeOneZone(SessionFactory factory, int type, String foreignKey,int allFlag) {
    	WaterReportMapper waterReportMapper = factory.getMapper(WaterReportMapper.class);    	
    	TreeMapper mapper = factory.getMapper(TreeMapper.class);	
		LongTreeBean node=mapper.getBeanByForeignIdType(type,foreignKey);
		if(node == null) {
			return null;
		}
		else{
			List<TreeZoneVO> zoneList=waterReportMapper.queryTreeOneZone(node);
			//是否要包含全网
			if(zoneList!=null && zoneList.size()>0) {
				if(allFlag==1) {
					//查询parentMask为0 的并且type为1 的foreignkey
					List<TreefirstVO> firstTreeVO=waterReportMapper.getFirstTree(treeType,parentmask);
					if(firstTreeVO==null || firstTreeVO.size()<1) {
						return null;
					}
					//赋值全网
					for(int y=0;y<zoneList.size();y++) {
						if("".equals(zoneList.get(y).getCode())|| zoneList.get(y).getCode()==null) {
							zoneList.get(y).setCode(firstTreeVO.get(0).getForeignkey());
							zoneList.get(y).setName("全网");
						}
					}
				}else {
					//过滤名称为空的
					zoneList=zoneList.stream().filter(s->(!"".equals(s.getName())&& s.getName()!=null)).collect(Collectors.toList());
				}
			}
			return zoneList;
		}
	}

    //一级分区产销差率比较报表
    @TaskAnnotation("queryOneZoneCXC")
	@Override
	public Map<String,Object> queryOneZoneCXC(SessionFactory factory, IndicatorNewDTO indicatorNewDTO) {
    	/**
		  * BALANCE_INDIC  水平衡基础数据--- 抄表量  WNMBMC
		         供水（总）量 1     WNMFWSSITDF 
		  * 							
		  * ZONE_LOSS_INDIC 分区漏损数据   ---  产销差率1 WNMNRR
		  * 
		  */
    	WaterReportMapper waterReportMapper = factory.getMapper(WaterReportMapper.class);
    	TreeMapper mapper = factory.getMapper(TreeMapper.class);	
    	List<String>  monthList=new ArrayList<>();
		try {
			//获取中间月份
		   monthList=getMonthBetween(indicatorNewDTO.getStartTime().toString(),indicatorNewDTO.getEndTime().toString());
		} catch (Exception e) {			
			e.printStackTrace();
		}
		//返回的数据
		List<WB2OneZoneVO> wB2OneZoneList=new ArrayList<>();
		//查询parentMask为0 的并且type为1 的foreignkey
		List<TreefirstVO> firstTreeVO=waterReportMapper.getFirstTree(treeType,parentmask);
		LongTreeBean node=null;
		List<TreeZoneVO> zoneList=new ArrayList<>();
		List<String> finalZoneList=new ArrayList<>();
		if(firstTreeVO!=null  && firstTreeVO.size()>0) {
		 //查询一级分区
		 node=mapper.getBeanByForeignIdType(treeType,firstTreeVO.get(0).getForeignkey());
		}else {
			return null;
		}
		if(node == null) {
			return null;
		}
		else{
			zoneList=waterReportMapper.queryTreeOneZone(node);		
			if(zoneList!=null && zoneList.size()>0) {
				for(int i=0;i<zoneList.size();i++) {
					if(zoneList.get(i).getName()!=null && !"".equals(zoneList.get(i).getName())) {
					 finalZoneList.add(zoneList.get(i).getCode());
					}
		    	}
			}
		}
		
		//一级分区
	    for(int i=0;i<monthList.size();i++) {	
	    	WB2OneZoneVO wB2OneZoneVO=new WB2OneZoneVO();
	    	wB2OneZoneVO.setMonthId(monthList.get(i));
	    	//查询水平衡数据 抄表量  
	    	IndicatorNewDTO indicatorcb=new IndicatorNewDTO();
	    	List<String> balanceCBCodes=new ArrayList<>();
	    	balanceCBCodes.add(Constant.BALANCE_INDIC_FLMBMC);
	    	indicatorcb.setCodes(balanceCBCodes);
	    	indicatorcb.setStartTime(Integer.valueOf(monthList.get(i)));
	    	indicatorcb.setEndTime(Integer.valueOf(monthList.get(i)));
	    	indicatorcb.setTimeType(timeType);
	    	//添加一级分区
	    	indicatorcb.setZoneCodes(finalZoneList);
	    	
	   	    List<IndicatorVO> balanceCBList=waterReportMapper.queryWBBaseIndicData(indicatorcb);
	   	    if(balanceCBList!=null && balanceCBList.size()>0) {
	   	    	for(int j=0;j<balanceCBList.size();j++) {	   	    		
	   	    		balanceCBList.get(j).setValue(new BigDecimal(balanceCBList.get(j).getValue()/10000).setScale(2, RoundingMode.UP).doubleValue());
	   	    	}
	   	    }
	   	    wB2OneZoneVO.setCbList(balanceCBList);
	   	    
	       //查询水平衡数据 供水量  
	    	IndicatorNewDTO indicatorgs=new IndicatorNewDTO();
	    	List<String> balanceGSCodes=new ArrayList<>();	    
	    	balanceGSCodes.add(Constant.BALANCE_INDIC_FLMFWSSITDF);
	    	indicatorgs.setCodes(balanceGSCodes);
	    	indicatorgs.setStartTime(Integer.valueOf(monthList.get(i)));
	    	indicatorgs.setEndTime(Integer.valueOf(monthList.get(i)));
	    	indicatorgs.setTimeType(timeType);
	    	
	    	indicatorgs.setZoneCodes(finalZoneList);
	    	
	   	    List<IndicatorVO> balanceGSList=waterReportMapper.queryWBBaseIndicData(indicatorgs);
	   	   if(balanceGSList!=null && balanceGSList.size()>0) {
	   	    	for(int j=0;j<balanceGSList.size();j++) {	   	    		
	   	    		balanceGSList.get(j).setValue(new BigDecimal(balanceGSList.get(j).getValue()/10000).setScale(2, RoundingMode.UP).doubleValue());
	   	    	}
	   	    }
	   	    wB2OneZoneVO.setGslList(balanceGSList);
	   	    
	   	    //查询产销差率
	   	    IndicatorNewDTO indicatorCXC=new IndicatorNewDTO();
	    	List<String> zoneCXCCodes=new ArrayList<>();
	    	zoneCXCCodes.add(Constant.ZONE_LOSS_INDIC_FLMNRR);
	    	indicatorCXC.setCodes(zoneCXCCodes);
	    	indicatorCXC.setStartTime(Integer.valueOf(monthList.get(i)));
	    	indicatorCXC.setEndTime(Integer.valueOf(monthList.get(i)));
	    	indicatorCXC.setTimeType(timeType);
	    	
	    	indicatorCXC.setZoneCodes(finalZoneList);
	    	
	    	List<IndicatorVO> zoneLossCXCList=waterReportMapper.queryZoneLossIndicData(indicatorCXC);
	    	if(zoneLossCXCList!=null && zoneLossCXCList.size()>0) {
	   	    	for(int j=0;j<zoneLossCXCList.size();j++) {	   	    		
	   	    		zoneLossCXCList.get(j).setValue(Math.ceil(zoneLossCXCList.get(j).getValue()*100*100)/100);
	   	    	}
	   	    }
	    	wB2OneZoneVO.setCxcList(zoneLossCXCList);
	    	 
	    	
	    	wB2OneZoneList.add(wB2OneZoneVO);	           	    
	    }
	    
	  //分页 action
	      Map<String,Object> finalMap=new HashMap<>();
	      
	      finalMap.put("rowNumber", wB2OneZoneList.size());
		 
		  int startIndex=(indicatorNewDTO.getPage()-1)*indicatorNewDTO.getPageCount();
		  int listsize=wB2OneZoneList.size();
		  
		  //新建一个空的List集合接收它  	
		  List<WB2OneZoneVO> copyList=new ArrayList<>();
		  if(Math.floor(listsize/indicatorNewDTO.getPageCount())>=indicatorNewDTO.getPage()) {
			  listsize=startIndex+indicatorNewDTO.getPageCount();
		  }
		  for(int n=startIndex;n<listsize;n++) {
			  copyList.add(wB2OneZoneList.get(n));
		  }
		 // 插入分页信息
		  PageVO pageVO = PageUtil.getPageBean(indicatorNewDTO.getPage(), indicatorNewDTO.getPageCount(), listsize);
		  finalMap.put("pageCount", indicatorNewDTO.getPageCount());
		  finalMap.put("page", indicatorNewDTO.getPage());
		  finalMap.put("totalPage", pageVO.getRowNumber());
		  finalMap.put("dataList",copyList);
		  
	    //end
    	
		//return wB2OneZoneList;
		  return finalMap;
	}

    //WB_03)二级分区水平衡报表
    @TaskAnnotation("queryTwoZoneWater")
	@Override
	public Map<String, Object> queryTwoZoneWater(SessionFactory factory, IndicatorNewDTO indicatorNewDTO) {
    	
    	/**
		  * BALANCE_INDIC  水平衡基础数据--- 抄表量  WNMBMC
		         供水（总）量 1     WNMFWSSITDF 
		  * 							
		  * ZONE_LOSS_INDIC 分区漏损数据   ---  产销差率1 WNMNRR
		  * 
		  */
    	
    	WaterReportMapper waterReportMapper = factory.getMapper(WaterReportMapper.class);
    	TreeMapper mapper = factory.getMapper(TreeMapper.class);
    	List<Map<String, Object>> finalList=new ArrayList<Map<String, Object>>();  	
    	Map<String,Object> finalMap=new HashMap<>();   	
    	//根据一级分区查询是否存在二级分区,如果不存在二级分区的话,报表不显示了
    	LongTreeBean node=mapper.getBeanByForeignIdType(2,indicatorNewDTO.getZoneCodes().get(0));
    	//下一级分区集合(二级分区)
    	List<TreeZoneVO> zoneList=new ArrayList<>();
    	
    	//一级分区名称
    	String oneZoneName="";
    	if(node == null) {
			return null;
		}
		else{
			zoneList=waterReportMapper.queryTreeOneZone(node);
			if(zoneList==null || zoneList.size()<2) {
				return null;
			}
			for(int y=0;y<zoneList.size();y++) {
				if(zoneList.get(y).getCode().equals(indicatorNewDTO.getZoneCodes().get(0))) {
					oneZoneName=zoneList.get(y).getName();
					break;
				}
			}
			zoneList=zoneList.stream().filter(s->(!indicatorNewDTO.getZoneCodes().get(0).equals(s.getCode()))).collect(Collectors.toList());
		}
    	
    	
    	List<String>  monthList=new ArrayList<>();
		try {
			//获取中间月份
		   monthList=getMonthBetween(indicatorNewDTO.getStartTime().toString(),indicatorNewDTO.getEndTime().toString());
		} catch (Exception e) {			
			e.printStackTrace();
		}		     
		//遍历月份
		//for(int i=0;i<monthList.size();i++) {				
			  //遍历二级分区
				for(int p=0;p<zoneList.size();p++) {
				   for(int index=0;index<3;index++) {
					 Map<String, Object> bean=new HashMap<>();
					 //设置一级分区名
					 bean.put("oneZoneName", oneZoneName);
					 //创建月份
					 for(int m=0;m<monthList.size();m++) {
						 bean.put(monthList.get(m),"-");
					 }
					//分区编号
			    	List<String> oneZoneList=new ArrayList<>();
			    	oneZoneList.add(zoneList.get(p).getCode());	
			    	//塞二级分区名
			    	bean.put("secondZoneName",zoneList.get(p).getName());
			    	//塞二级分区code
			    	bean.put("secondZoneCode",zoneList.get(p).getCode());
					if(index==0) {	
						//塞漏损指标
						bean.put("zoneLossIndicator",Constant.BALANCE_INDIC_SLMFWSSITDF);
						//查询水平衡数据 供水量  
				    	IndicatorNewDTO indicatorgs=new IndicatorNewDTO();
				    	List<String> balanceGSCodes=new ArrayList<>();	    
				    	balanceGSCodes.add(Constant.BALANCE_INDIC_SLMFWSSITDF);
				    	indicatorgs.setCodes(balanceGSCodes);
				    	indicatorgs.setStartTime(indicatorNewDTO.getStartTime());
				    	indicatorgs.setEndTime(indicatorNewDTO.getEndTime());
				    	indicatorgs.setTimeType(timeType);
				    	
				    	indicatorgs.setZoneCodes(oneZoneList);				    	
				    	List<IndicatorVO> balanceGSList=waterReportMapper.queryWBBaseIndicData(indicatorgs);
						
				    	 if(balanceGSList!=null && balanceGSList.size()>0) {
					   	    	for(int j=0;j<balanceGSList.size();j++) {	   	    		
					   	    		balanceGSList.get(j).setValue(new BigDecimal(balanceGSList.get(j).getValue()/10000).setScale(2, RoundingMode.UP).doubleValue());
					   	    	}
					   	    }
				    	//处理数据 	
				    	 if(balanceGSList!=null && balanceGSList.size()>0) {
				    			for(int j=0;j<balanceGSList.size();j++) {				    				
				    				for(int k=0;k<monthList.size();k++) {
				    					if(balanceGSList.get(j).getTimeId().toString().equals(monthList.get(k))) {
				    					    bean.put(monthList.get(k), balanceGSList.get(j).getValue());
				    						break;
				    					}
				    				}
				    			}
				    	 }
					   					 
					   
					}else if(index==1) {
						//塞漏损指标
						bean.put("zoneLossIndicator",Constant.BALANCE_INDIC_SLMBMC);
						//查询水平衡数据 抄表量  
				    	IndicatorNewDTO indicatorcb=new IndicatorNewDTO();
				    	List<String> balanceCBCodes=new ArrayList<>();
				    	balanceCBCodes.add(Constant.BALANCE_INDIC_SLMBMC);
				    	indicatorcb.setCodes(balanceCBCodes);
				    	indicatorcb.setStartTime(indicatorNewDTO.getStartTime());
				    	indicatorcb.setEndTime(indicatorNewDTO.getEndTime());
				    	indicatorcb.setTimeType(timeType);
				    	
				    	//添加分区编码
				    	indicatorcb.setZoneCodes(oneZoneList);
				   	    List<IndicatorVO> balanceCBList=waterReportMapper.queryWBBaseIndicData(indicatorcb);	
						
				    	 if(balanceCBList!=null && balanceCBList.size()>0) {
				   	    	for(int j=0;j<balanceCBList.size();j++) {	   	    		
				   	    		balanceCBList.get(j).setValue(new BigDecimal(balanceCBList.get(j).getValue()/10000).setScale(2, RoundingMode.UP).doubleValue());
				   	    	}
				   	    }
				    	 
				    	//处理数据 	
				    	 if(balanceCBList!=null && balanceCBList.size()>0) {
				    			for(int j=0;j<balanceCBList.size();j++) {				    				
				    				for(int k=0;k<monthList.size();k++) {
				    					if(balanceCBList.get(j).getTimeId().toString().equals(monthList.get(k))) {
				    					    bean.put(monthList.get(k), balanceCBList.get(j).getValue());
				    						break;
				    					}
				    				}
				    			}
				    	 }
					 
					}else {
						//塞漏损指标
						bean.put("zoneLossIndicator",Constant.ZONE_LOSS_INDIC_SLMNRR);
						//查询产销差率
				   	    IndicatorNewDTO indicatorCXC=new IndicatorNewDTO();
				    	List<String> zoneCXCCodes=new ArrayList<>();
				    	zoneCXCCodes.add(Constant.ZONE_LOSS_INDIC_SLMNRR);
				    	indicatorCXC.setCodes(zoneCXCCodes);
				    	indicatorCXC.setStartTime(indicatorNewDTO.getStartTime());
				    	indicatorCXC.setEndTime(indicatorNewDTO.getEndTime());
				    	indicatorCXC.setTimeType(timeType);
				    	
				    	indicatorCXC.setZoneCodes(oneZoneList);
				    				    	
				    	List<IndicatorVO> zoneLossCXCList=waterReportMapper.queryZoneLossIndicData(indicatorCXC);
				    	
				    	if(zoneLossCXCList!=null && zoneLossCXCList.size()>0) {
				   	    	for(int j=0;j<zoneLossCXCList.size();j++) {	   	  			   	    		
				   	    		zoneLossCXCList.get(j).setValue(Math.ceil(zoneLossCXCList.get(j).getValue()*100*100)/100);
				   	    	}
				   	    }
				    	
				    	//处理数据 	
				    	 if(zoneLossCXCList!=null && zoneLossCXCList.size()>0) {
				    			for(int j=0;j<zoneLossCXCList.size();j++) {				    				
				    				for(int k=0;k<monthList.size();k++) {
				    					if(zoneLossCXCList.get(j).getTimeId().toString().equals(monthList.get(k))) {
				    					    bean.put(monthList.get(k), zoneLossCXCList.get(j).getValue());
				    						break;
				    					}
				    				}
				    			}
				    	 }
					  }	
					  finalList.add(bean);
					}
					
				}
				
		//}
		
		 finalMap.put("rowNumber", finalList.size());
		 
		  int startIndex=(indicatorNewDTO.getPage()-1)*indicatorNewDTO.getPageCount();
		  int listsize=finalList.size();
		  
		  //新建一个空的List集合接收它  	
		  List<Map<String, Object>> copyList=new ArrayList<>();
		  if(Math.floor(listsize/indicatorNewDTO.getPageCount())>=indicatorNewDTO.getPage()) {
			  listsize=startIndex+indicatorNewDTO.getPageCount();
		  }
		  for(int n=startIndex;n<listsize;n++) {
			  copyList.add(finalList.get(n));
		  }
		 // 插入分页信息
		  PageVO pageVO = PageUtil.getPageBean(indicatorNewDTO.getPage(), indicatorNewDTO.getPageCount(), listsize);
		  finalMap.put("pageCount", indicatorNewDTO.getPageCount());
		  finalMap.put("page", indicatorNewDTO.getPage());
		  finalMap.put("totalPage", pageVO.getRowNumber());
		  finalMap.put("dataList",copyList);
		  
		  return finalMap;
		 // return finalList;
	}

	
	
}

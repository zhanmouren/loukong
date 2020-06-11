package com.koron.inwlms.service.indexData.impl;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.common.web.mapper.LongTreeBean;
import com.koron.common.web.mapper.TreeMapper;
import com.koron.indicator.service.ZoneLossIndicatorService;
import com.koron.inwlms.bean.DTO.common.IndicatorDTO;
import com.koron.inwlms.bean.DTO.indexData.IndicatorNewDTO;
import com.koron.inwlms.bean.DTO.indexData.WarningInfoDTO;
import com.koron.inwlms.bean.VO.common.IndicatorVO;
import com.koron.inwlms.bean.VO.common.PageVO;
import com.koron.inwlms.bean.VO.indexData.InfoCompleteRateVO;
import com.koron.inwlms.bean.VO.indexData.InfoPageListVO;
import com.koron.inwlms.bean.VO.indexData.MultParamterIndicatorVO;
import com.koron.inwlms.bean.VO.indexData.TaskMsgVO;
import com.koron.inwlms.bean.VO.indexData.TreeZoneVO;
import com.koron.inwlms.bean.VO.sysManager.TreeMenuVO;
import com.koron.inwlms.mapper.indexData.IndexMapper;
import com.koron.inwlms.mapper.sysManager.UserMapper;
import com.koron.inwlms.service.indexData.IndexService;
import com.koron.inwlms.util.PageUtil;
import com.koron.util.Constant;

@Service
public class IndexServiceImpl implements IndexService{
	public Integer timeTypeMonth=1;
	public Integer timeTypeYear=2;
	public Integer yearType=0;
	public Integer monthType=1;
	//查询首页的综合信息
	@TaskAnnotation("queryCompreInfo")
	@Override
	public List<IndicatorVO> queryCompreInfo(SessionFactory factory,IndicatorNewDTO indicatorDTO) {
			IndexMapper indicatorMapper = factory.getMapper(IndexMapper.class);
			List<IndicatorVO> finalList=new ArrayList<>();
			//TODO 根据分区编号，查询是该分区属于哪个级别（全网，一级，二级还是DMA）
			/**
			管网长度  用户数              ------基础指标-水司用户 BASE_INDIC
			供水量  月 年                    -------水平衡基础数据  BALANCE_INDIC
			抄表量  月 年                    -------水平衡基础数据  BALANCE_INDIC
			产销差  月 年                    -------水平衡基础数据  BALANCE_INDIC
			产销差率  月 年                 -------分区漏损数据   ZONE_LOSS_INDIC
			漏损量  年月                       -------表观漏损数据  APPARENT_INDIC
			漏损率  （年月）             -------分区漏损数据  ZONE_LOSS_INDIC
			*/
			List<IndicatorVO> indBaseIndicMList=new ArrayList<>();
			List<IndicatorVO> indBalanceMList=new ArrayList<>();
			List<IndicatorVO> indBalanceYList=new ArrayList<>();
			List<IndicatorVO> indZoneLossMList=new ArrayList<>();
			List<IndicatorVO> indZoneLossYList=new ArrayList<>();
			//上一月指标
			List<IndicatorVO> indZoneLossLMList=new ArrayList<>();
			//上一年指标
			List<IndicatorVO> indZoneLossLYList=new ArrayList<>();
			
			//上个月
			Integer lastStartTime=null;
			Integer lastEndTime=null;
			if(Integer.valueOf(indicatorDTO.getStartTime().toString().substring(4, 6))==10) {
				 lastStartTime=Integer.valueOf(indicatorDTO.getStartTime().toString().substring(0, 4)+"09");
				 lastEndTime=lastStartTime;
			}
			else if(Integer.valueOf(indicatorDTO.getStartTime().toString().substring(4, 6))==1) {
				 lastStartTime=Integer.valueOf(Integer.valueOf(indicatorDTO.getStartTime().toString().substring(0, 4))-1+"12");
				 lastEndTime=lastStartTime;
			}else if(Integer.valueOf(indicatorDTO.getStartTime().toString().substring(4, 6))>10) {
				Integer lastMonth=Integer.valueOf(indicatorDTO.getStartTime().toString().substring(4, 6))-1;
				 lastStartTime=Integer.valueOf(indicatorDTO.getStartTime().toString().substring(0, 4)+lastMonth.toString());
				 lastEndTime=lastStartTime;
			}else if(Integer.valueOf(indicatorDTO.getStartTime().toString().substring(4, 6))<10) {
				Integer lastMonth=Integer.valueOf(indicatorDTO.getStartTime().toString().substring(4, 6))-1;
				 lastStartTime=Integer.valueOf(indicatorDTO.getStartTime().toString().substring(0, 4)+"0"+lastMonth.toString());
				 lastEndTime=lastStartTime;
			}
			
			//上一年
			Integer lastYearStartTime=2020;
			Integer lastYearEndTime=2020;
			lastYearStartTime=Integer.valueOf(indicatorDTO.getStartTime().toString().substring(0, 4))-1;
			lastYearEndTime=lastYearStartTime;
			int areaType = indicatorDTO.getAreaType();
			//设置指标编码
			
				//查询基础指标的数据 (月)
				List<String> baseIndicList=new ArrayList<>();
						
				if(areaType==0) {//如果是全网
				  //管长(月)
				  baseIndicList.add(Constant.BASE_INDIC_WNMFTPL);
				 //用户数(月)
				  baseIndicList.add(Constant.BASE_INDIC_WNMNOCM);
				}else if(areaType==1) {  //一级分区
				  //管长(月)
				  baseIndicList.add(Constant.BASE_INDIC_FLMFTPL);
				//用户数(月)
				  baseIndicList.add(Constant.BASE_INDIC_FLMNOCM);	
				}else if(areaType==2) { //二级分区
				  //管长(月)
				  baseIndicList.add(Constant.BASE_INDIC_SLMFTPL);
				//用户数(月)
				  baseIndicList.add(Constant.BASE_INDIC_SLMNOCM);
				}else {  //DMA
				  //管长(月)
				 baseIndicList.add(Constant.BASE_INDIC_DMMFTPL);	
				//用户数(月)
				 baseIndicList.add(Constant.BASE_INDIC_DMMNOCM);
				}
				
				indicatorDTO.setCodes(baseIndicList);
				indicatorDTO.setTimeType(timeTypeMonth);
				if(areaType==0) {
					indBaseIndicMList=indicatorMapper.queryCompanyIndicData(indicatorDTO);
				}
				else {
					indBaseIndicMList=indicatorMapper.queryBaseIndicData(indicatorDTO);
				}
			    
			    
			    //拿出indBaseIndicMList
			    if(indBaseIndicMList!=null && indBaseIndicMList.size()>0) {
			     for(int m=0;m<indBaseIndicMList.size();m++) {
			         	if(areaType==0) {//如果是全网
						  //管长(月)
						  if(Constant.BASE_INDIC_WNMFTPL.equals(indBaseIndicMList.get(m).getCode())) {
							  indBaseIndicMList.get(m).setType("A1");							  
						  }
					      //用户数(月)
						  if(Constant.BASE_INDIC_WNMNOCM.equals(indBaseIndicMList.get(m).getCode())) {
							  indBaseIndicMList.get(m).setType("A2");	 
						  }
						  
						}else if(areaType==1) {  //一级分区						
						  //管长(月)
						  if(Constant.BASE_INDIC_FLMFTPL.equals(indBaseIndicMList.get(m).getCode())) {
							  indBaseIndicMList.get(m).setType("A1");							  
						  }
					      //用户数(月)
						  if(Constant.BASE_INDIC_FLMNOCM.equals(indBaseIndicMList.get(m).getCode())) {
							  indBaseIndicMList.get(m).setType("A2");	 
						  }
						}else if(areaType==2) { //二级分区
						  //管长(月)
						  if(Constant.BASE_INDIC_SLMFTPL.equals(indBaseIndicMList.get(m).getCode())) {
							  indBaseIndicMList.get(m).setType("A1");							  
						  }
					      //用户数(月)
						  if(Constant.BASE_INDIC_SLMNOCM.equals(indBaseIndicMList.get(m).getCode())) {
							  indBaseIndicMList.get(m).setType("A2");	 
						  }
						}else {  //DMA
						  //管长(月)
						  if(Constant.BASE_INDIC_DMMFTPL.equals(indBaseIndicMList.get(m).getCode())) {
							  indBaseIndicMList.get(m).setType("A1");							  
						  }
					      //用户数(月)
						  if(Constant.BASE_INDIC_DMMNOCM.equals(indBaseIndicMList.get(m).getCode())) {
							  indBaseIndicMList.get(m).setType("A2");	 
						  }
						}
			      }
			    }
			    
				
				//清空条件
				//indicatorDTO.setCodes(new ArrayList<String>());
				
				// 查询水平衡基础数据 (月)
				List<String> balanceMList=new ArrayList<>();
				
				if(areaType==0) { //全网		
				 //供水量(月)
				  balanceMList.add(Constant.BALANCE_INDIC_WNMFWSSITDF);
				 //抄表量(月)
				  balanceMList.add(Constant.BALANCE_INDIC_WNMBMC);	
				 //产销差（月）
				 balanceMList.add(Constant.BALANCE_INDIC_WNMNRW);
				}else if(areaType==1) { //一级分区	
				 //供水量(月)
				 balanceMList.add(Constant.BALANCE_INDIC_FLMFWSSITDF);
				//抄表量(月)
				 balanceMList.add(Constant.BALANCE_INDIC_FLMBMC);	
				//产销差（月）
				 balanceMList.add(Constant.BALANCE_INDIC_FLMNRW);
				}else if(areaType==2) { //二级分区	
				 //供水量(月)
			     balanceMList.add(Constant.BALANCE_INDIC_SLMFWSSITDF);
			     //抄表量(月)
			     balanceMList.add(Constant.BALANCE_INDIC_SLMBMC);
			   //产销差（月）
			     balanceMList.add(Constant.BALANCE_INDIC_SLMNRW);	
				}else { //DMA	
				  //供水量(月)
				  balanceMList.add(Constant.BALANCE_INDIC_DMMFWSSITDF);
				  //抄表量(月)
				  balanceMList.add(Constant.BALANCE_INDIC_DMMBMC);
				//产销差（月）
				  balanceMList.add(Constant.BALANCE_INDIC_DMMNRW);
				}						
					
				indicatorDTO.setCodes(balanceMList);
				indicatorDTO.setTimeType(timeTypeMonth);
				if(areaType==0) {
					indBalanceMList=indicatorMapper.queryCompanyIndicData(indicatorDTO);
				}
				else {
					indBalanceMList=indicatorMapper.queryWBBaseIndicData(indicatorDTO);	
				}
			    
			
			    //取出数据
			    if(indBalanceMList!=null && indBalanceMList.size()>0) {
				    for(int k=0;k<indBalanceMList.size();k++) {
				    	if(areaType==0) { //全网								 
							 //供水量(月)
							 if(Constant.BALANCE_INDIC_WNMFWSSITDF.equals(indBalanceMList.get(k).getCode())) {
								 indBalanceMList.get(k).setType("B1");							  
							  }
							 //抄表量(月)
							 if(Constant.BALANCE_INDIC_WNMBMC.equals(indBalanceMList.get(k).getCode())) {
								 indBalanceMList.get(k).setType("C1");							  
							 }
							 //产销差（月）
							 if(Constant.BALANCE_INDIC_WNMNRW.equals(indBalanceMList.get(k).getCode())) {
								 indBalanceMList.get(k).setType("D1");							  
							 }
							 
							}else if(areaType==1) { //一级分区	
							 //供水量(月)
							 if(Constant.BALANCE_INDIC_FLMFWSSITDF.equals(indBalanceMList.get(k).getCode())) {
								 indBalanceMList.get(k).setType("B1");							  
							  }
							 //抄表量(月)
							 if(Constant.BALANCE_INDIC_FLMBMC.equals(indBalanceMList.get(k).getCode())) {
								 indBalanceMList.get(k).setType("C1");							  
							 }
							 //产销差（月）
							 if(Constant.BALANCE_INDIC_FLMNRW.equals(indBalanceMList.get(k).getCode())) {
								 indBalanceMList.get(k).setType("D1");							  
							 }
							}else if(areaType==2) { //二级分区	
						     //供水量(月)
							 if(Constant.BALANCE_INDIC_SLMFWSSITDF.equals(indBalanceMList.get(k).getCode())) {
								 indBalanceMList.get(k).setType("B1");							  
							  }
							 //抄表量(月)
							 if(Constant.BALANCE_INDIC_SLMBMC.equals(indBalanceMList.get(k).getCode())) {
								 indBalanceMList.get(k).setType("C1");							  
							 }
							 //产销差（月）
							 if(Constant.BALANCE_INDIC_SLMNRW.equals(indBalanceMList.get(k).getCode())) {
								 indBalanceMList.get(k).setType("D1");							  
							 }
							}else { //DMA	
							  //供水量(月)
								 if(Constant.BALANCE_INDIC_DMMFWSSITDF.equals(indBalanceMList.get(k).getCode())) {
									 indBalanceMList.get(k).setType("B1");							  
								  }
								 //抄表量(月)
								 if(Constant.BALANCE_INDIC_DMMBMC.equals(indBalanceMList.get(k).getCode())) {
									 indBalanceMList.get(k).setType("C1");							  
								 }
								 //产销差（月）
								 if(Constant.BALANCE_INDIC_DMMNRW.equals(indBalanceMList.get(k).getCode())) {
									 indBalanceMList.get(k).setType("D1");							  
								 }
							}						
				    }
			    }
				
				//清空条件
			//	indicatorDTO.setCodes(new ArrayList<String>());			
				
				// 查询水平衡基础数据 (年)
				List<String> balanceYList=new ArrayList<>();												
				if(areaType==0) {  //全网
					//供水量(年)
					balanceYList.add(Constant.BALANCE_INDIC_WNYFWSSITDF);
					//抄表量(年)
					balanceYList.add(Constant.BALANCE_INDIC_WNYBMC);
					//产销差（年）
					balanceYList.add(Constant.BALANCE_INDIC_WNYNRW);
				}else if(areaType==1) { //一级分区
					//供水量(年)
					balanceYList.add(Constant.BALANCE_INDIC_FLYFWSSITDF);
					//抄表量(年)
					balanceYList.add(Constant.BALANCE_INDIC_FLYBMC);
					//产销差（年）
					balanceYList.add(Constant.BALANCE_INDIC_FLYNRW);
				}else if(areaType==2) {  //二级分区
					//供水量(年)
					balanceYList.add(Constant.BALANCE_INDIC_SLYFWSSITDF);
					//抄表量(年)
					balanceYList.add(Constant.BALANCE_INDIC_SLYBMC);
					//产销差（年）
					balanceYList.add(Constant.BALANCE_INDIC_SLYNRW);
				}else {      //DMA
					//供水量(年)
					balanceYList.add(Constant.BALANCE_INDIC_DMYFWSSITDF);
					//抄表量(年)
					balanceYList.add(Constant.BALANCE_INDIC_DMYBMC);
					//产销差（年）
					balanceYList.add(Constant.BALANCE_INDIC_DMYNRW);
				}
							
				indicatorDTO.setCodes(balanceYList);
				indicatorDTO.setTimeType(timeTypeYear);
				//截取年
				indicatorDTO.setStartTime(Integer.valueOf(indicatorDTO.getStartTime().toString().substring(0, 4)));
				indicatorDTO.setEndTime(Integer.valueOf(indicatorDTO.getEndTime().toString().substring(0, 4)));
				
				if(areaType==0) {
					indBalanceYList=indicatorMapper.queryCompanyIndicData(indicatorDTO);
				}
				else {
					indBalanceYList=indicatorMapper.queryWBBaseIndicData(indicatorDTO);	
				}
			   
			    
				
				
				//取出数据
				if(indBalanceYList!=null && indBalanceYList.size()>0) {
					for(int u=0;u<indBalanceYList.size();u++) {											
						if(areaType==0) {  //全网
							//供水量(年)
							if(Constant.BALANCE_INDIC_WNYFWSSITDF.equals(indBalanceYList.get(u).getCode())) {
								indBalanceYList.get(u).setType("B2");
							}
							//抄表量(年)
							if(Constant.BALANCE_INDIC_WNYBMC.equals(indBalanceYList.get(u).getCode())) {
								indBalanceYList.get(u).setType("C2");
							}
							//产销差（年）
							if(Constant.BALANCE_INDIC_WNYNRW.equals(indBalanceYList.get(u).getCode())) {
								indBalanceYList.get(u).setType("D2");
							}
						}else if(areaType==1) { //一级分区
							//供水量(年)
							if(Constant.BALANCE_INDIC_FLYFWSSITDF.equals(indBalanceYList.get(u).getCode())) {
								indBalanceYList.get(u).setType("B2");
							}
							//抄表量(年)
							if(Constant.BALANCE_INDIC_FLYBMC.equals(indBalanceYList.get(u).getCode())) {
								indBalanceYList.get(u).setType("C2");
							}
							//产销差（年）
							if(Constant.BALANCE_INDIC_FLYNRW.equals(indBalanceYList.get(u).getCode())) {
								indBalanceYList.get(u).setType("D2");
							}
						}else if(areaType==2) {  //二级分区
							//供水量(年)
							if(Constant.BALANCE_INDIC_SLYFWSSITDF.equals(indBalanceYList.get(u).getCode())) {
								indBalanceYList.get(u).setType("B2");
							}
							//抄表量(年)
							if(Constant.BALANCE_INDIC_SLYBMC.equals(indBalanceYList.get(u).getCode())) {
								indBalanceYList.get(u).setType("C2");
							}
							//产销差（年）
							if(Constant.BALANCE_INDIC_SLYNRW.equals(indBalanceYList.get(u).getCode())) {
								indBalanceYList.get(u).setType("D2");
							}
						}else {      //DMA
							//供水量(年)
							if(Constant.BALANCE_INDIC_DMYFWSSITDF.equals(indBalanceYList.get(u).getCode())) {
								indBalanceYList.get(u).setType("B2");
							}
							//抄表量(年)
							if(Constant.BALANCE_INDIC_DMYBMC.equals(indBalanceYList.get(u).getCode())) {
								indBalanceYList.get(u).setType("C2");
							}
							//产销差（年）
							if(Constant.BALANCE_INDIC_DMYNRW.equals(indBalanceYList.get(u).getCode())) {
								indBalanceYList.get(u).setType("D2");
							}
						}
					}
				}
				//清空条件
				
				//分区漏损数据 (月)
				List<String> zoneLossMList=new ArrayList<>();
				if(areaType==0) {  //全网
				//产销差率  月 			
				zoneLossMList.add(Constant.ZONE_LOSS_INDIC_WNMNRR);		
				//漏损率   月
				zoneLossMList.add(Constant.ZONE_LOSS_INDIC_WNMMNFTDF);	
				//漏损量 月
				zoneLossMList.add(Constant.APPARENT_INDIC_WNMAL);
				}else if(areaType==1) { //一级分区
				//产销差率  月 			
			     zoneLossMList.add(Constant.ZONE_LOSS_INDIC_FLMNRR);		
			     //漏损率   月
				zoneLossMList.add(Constant.ZONE_LOSS_INDIC_FLMMNFTDF);	
				//漏损量 月
				zoneLossMList.add(Constant.APPARENT_INDIC_FLMAL);
				}else if(areaType==2) {  //二级分区
				//产销差率  月 			
				zoneLossMList.add(Constant.ZONE_LOSS_INDIC_SLMNRR);		
				//漏损率   月
				zoneLossMList.add(Constant.ZONE_LOSS_INDIC_SLMMNFTDF);	
				//漏损量 月
				zoneLossMList.add(Constant.APPARENT_INDIC_SLMAL);
				}else {      //DMA
				//产销差率  月 			
				zoneLossMList.add(Constant.ZONE_LOSS_INDIC_DMMNRR);		
				//漏损率   月
				zoneLossMList.add(Constant.ZONE_LOSS_INDIC_DMMMNFTDF);	
				//漏损量 月
				zoneLossMList.add(Constant.APPARENT_INDIC_DMMAL);				
				}		
				indicatorDTO.setCodes(zoneLossMList);
				indicatorDTO.setTimeType(timeTypeMonth);
				
				if(areaType==0) {
					indZoneLossMList=indicatorMapper.queryCompanyIndicData(indicatorDTO);
				}
				else {
					indZoneLossMList=indicatorMapper.queryZoneLossIndicData(indicatorDTO);
				}
				
				
				//取出数据
				if(indZoneLossMList!=null && indZoneLossMList.size()>0) {
					for(int v=0;v<indZoneLossMList.size();v++) {
						if(areaType==0) {  //全网
							//产销差率  月 		
							 if(Constant.ZONE_LOSS_INDIC_WNMNRR.equals(indZoneLossMList.get(v).getCode())) {
								 indZoneLossMList.get(v).setType("D3");
							 }
							//漏损率   月
							 if(Constant.ZONE_LOSS_INDIC_WNMMNFTDF.equals(indZoneLossMList.get(v).getCode())) {
								 indZoneLossMList.get(v).setType("E3");
							 }
							//漏损量 月
							 if(Constant.APPARENT_INDIC_WNMAL.equals(indZoneLossMList.get(v).getCode())) {
								 indZoneLossMList.get(v).setType("E1");
							 }
							}else if(areaType==1) { //一级分区
							//产销差率  月 		
							 if(Constant.ZONE_LOSS_INDIC_FLMNRR.equals(indZoneLossMList.get(v).getCode())) {
								 indZoneLossMList.get(v).setType("D3");
							 }
							//漏损率   月
							 if(Constant.ZONE_LOSS_INDIC_FLMMNFTDF.equals(indZoneLossMList.get(v).getCode())) {
								 indZoneLossMList.get(v).setType("E3");
							 }
							//漏损量 月
							 if(Constant.APPARENT_INDIC_FLMAL.equals(indZoneLossMList.get(v).getCode())) {
								 indZoneLossMList.get(v).setType("E1");
							 }
							}else if(areaType==2) {  //二级分区
							//产销差率  月 		
							 if(Constant.ZONE_LOSS_INDIC_SLMNRR.equals(indZoneLossMList.get(v).getCode())) {
								 indZoneLossMList.get(v).setType("D3");
							 }
							//漏损率   月
							 if(Constant.ZONE_LOSS_INDIC_SLMMNFTDF.equals(indZoneLossMList.get(v).getCode())) {
								 indZoneLossMList.get(v).setType("E3");
							 }
							//漏损量 月
							 if(Constant.APPARENT_INDIC_SLMAL.equals(indZoneLossMList.get(v).getCode())) {
								 indZoneLossMList.get(v).setType("E1");
							 }
							}else {      //DMA
							//产销差率  月 		
							 if(Constant.ZONE_LOSS_INDIC_DMMNRR.equals(indZoneLossMList.get(v).getCode())) {
								 indZoneLossMList.get(v).setType("D3");
							 }
							//漏损率   月
							 if(Constant.ZONE_LOSS_INDIC_DMMMNFTDF.equals(indZoneLossMList.get(v).getCode())) {
								 indZoneLossMList.get(v).setType("E3");
							 }
							//漏损量 月
							 if(Constant.APPARENT_INDIC_DMMAL.equals(indZoneLossMList.get(v).getCode())) {
								 indZoneLossMList.get(v).setType("E1");
							 }
							}		
					}
				}
				
				//清空条件
				//indicatorDTO.setCodes(new ArrayList<String>());
				
				//分区漏损数据 (年)
				List<String> zoneLossYList=new ArrayList<>();
				if(areaType==0) {  //全网
				//产销差率  年			
				zoneLossYList.add(Constant.ZONE_LOSS_INDIC_WNYNRR);		
				//漏损率  年
				zoneLossYList.add(Constant.ZONE_LOSS_INDIC_WNYMNFTDF);	
				//漏损量 年		
				zoneLossYList.add(Constant.APPARENT_INDIC_WNYAL);
				}else if(areaType==1) { //一级分区
				//产销差率  年			
				zoneLossYList.add(Constant.ZONE_LOSS_INDIC_FLYNRR);		
				//漏损率  年
				zoneLossYList.add(Constant.ZONE_LOSS_INDIC_FLYMNFTDF);	
				//漏损量 年		
				zoneLossYList.add(Constant.APPARENT_INDIC_FLYAL);
					
				}else if(areaType==2) {  //二级分区
				//产销差率  年			
				zoneLossYList.add(Constant.ZONE_LOSS_INDIC_SLYNRR);		
				//漏损率  年
				zoneLossYList.add(Constant.ZONE_LOSS_INDIC_SLYMNFTDF);	
				//漏损量 年		
				zoneLossYList.add(Constant.APPARENT_INDIC_SLYAL);
				}else {//DMA
				//产销差率  年			
				zoneLossYList.add(Constant.ZONE_LOSS_INDIC_DMYNRR);		
				//漏损率  年
				zoneLossYList.add(Constant.ZONE_LOSS_INDIC_DMYMNFTDF);	
				//漏损量 年		
				zoneLossYList.add(Constant.APPARENT_INDIC_DMYAL);
				}
				indicatorDTO.setCodes(zoneLossYList);
				//截取年
				indicatorDTO.setStartTime(Integer.valueOf(indicatorDTO.getStartTime().toString().substring(0, 4)));
				indicatorDTO.setEndTime(Integer.valueOf(indicatorDTO.getEndTime().toString().substring(0, 4)));
				indicatorDTO.setTimeType(timeTypeYear);
				if(areaType==0) {
					indZoneLossYList=indicatorMapper.queryCompanyIndicData(indicatorDTO);
				}
				else {
					indZoneLossYList=indicatorMapper.queryZoneLossIndicData(indicatorDTO);	
				}
			   
				
				//取出数据
				if(indZoneLossYList!=null && indZoneLossYList.size()>0) {
					for(int c=0;c<indZoneLossYList.size();c++) {
						if(areaType==0) {  //全网
							//产销差率  年	
							 if(Constant.ZONE_LOSS_INDIC_WNYNRR.equals(indZoneLossYList.get(c).getCode())) {
								 indZoneLossYList.get(c).setType("D4");
							 }
							 //漏损率  年
							 if(Constant.ZONE_LOSS_INDIC_WNYMNFTDF.equals(indZoneLossYList.get(c).getCode())) {
								 indZoneLossYList.get(c).setType("E4");
							 }
							 //漏损量 年		
							 if(Constant.APPARENT_INDIC_WNYAL.equals(indZoneLossYList.get(c).getCode())) {
								 indZoneLossYList.get(c).setType("E2");
							 }
							 
							}else if(areaType==1) { //一级分区
							//产销差率  年	
							 if(Constant.ZONE_LOSS_INDIC_FLYNRR.equals(indZoneLossYList.get(c).getCode())) {
								 indZoneLossYList.get(c).setType("D4");
							 }
							 //漏损率  年
							 if(Constant.ZONE_LOSS_INDIC_FLYMNFTDF.equals(indZoneLossYList.get(c).getCode())) {
								 indZoneLossYList.get(c).setType("E4");
							 }
							 //漏损量 年		
							 if(Constant.APPARENT_INDIC_FLYAL.equals(indZoneLossYList.get(c).getCode())) {
								 indZoneLossYList.get(c).setType("E2");
							 }
								
							}else if(areaType==2) {  //二级分区
							//产销差率  年	
							 if(Constant.ZONE_LOSS_INDIC_SLYNRR.equals(indZoneLossYList.get(c).getCode())) {
								 indZoneLossYList.get(c).setType("D4");
							 }
							 //漏损率  年
							 if(Constant.ZONE_LOSS_INDIC_SLYMNFTDF.equals(indZoneLossYList.get(c).getCode())) {
								 indZoneLossYList.get(c).setType("E4");
							 }
							 //漏损量 年		
							 if(Constant.APPARENT_INDIC_SLYAL.equals(indZoneLossYList.get(c).getCode())) {
								 indZoneLossYList.get(c).setType("E2");
							 }
						   }
							else {//DMA
							//产销差率  年	
							 if(Constant.ZONE_LOSS_INDIC_DMYNRR.equals(indZoneLossYList.get(c).getCode())) {
								 indZoneLossYList.get(c).setType("D4");
							 }
							 //漏损率  年
							 if(Constant.ZONE_LOSS_INDIC_DMYMNFTDF.equals(indZoneLossYList.get(c).getCode())) {
								 indZoneLossYList.get(c).setType("E4");
							 }
							 //漏损量 年		
							 if(Constant.APPARENT_INDIC_DMYAL.equals(indZoneLossYList.get(c).getCode())) {
								 indZoneLossYList.get(c).setType("E2");
							 }

						  }
						
						
					}
				}
				
				//--查询上个月的数据，上一年的数据--
				//清空条件
			//	indicatorDTO.setCodes(new ArrayList<String>());			
				//分区漏损数据 (上个月)
				List<String> zoneLossLYMList=new ArrayList<>();	
				if(areaType==0) { //全网
				//查询上个月的月产销差率
				zoneLossLYMList.add(Constant.ZONE_LOSS_INDIC_WNMNRR);			
				//查询上个月的月漏损差率
				zoneLossLYMList.add(Constant.ZONE_LOSS_INDIC_WNMMNFTDF);
				}else if(areaType==1) { //一级分区		
				//查询上个月的月产销差率
				zoneLossLYMList.add(Constant.ZONE_LOSS_INDIC_FLMNRR);			
				//查询上个月的月漏损差率
				zoneLossLYMList.add(Constant.ZONE_LOSS_INDIC_FLMMNFTDF);
				}else if(areaType==2) {  //二级分区
				//查询上个月的月产销差率
				zoneLossLYMList.add(Constant.ZONE_LOSS_INDIC_SLMNRR);			
				//查询上个月的月漏损差率
				zoneLossLYMList.add(Constant.ZONE_LOSS_INDIC_SLMMNFTDF);	
				}else {  //DMA
				//查询上个月的月产销差率
				zoneLossLYMList.add(Constant.ZONE_LOSS_INDIC_DMMNRR);			
				//查询上个月的月漏损差率
				zoneLossLYMList.add(Constant.ZONE_LOSS_INDIC_DMMMNFTDF);	
				}
				//设置查询条件
				indicatorDTO.setStartTime(lastStartTime);
				indicatorDTO.setEndTime(lastEndTime);
				indicatorDTO.setCodes(zoneLossLYMList);
				indicatorDTO.setTimeType(timeTypeMonth);
				if(areaType==0) {
					indZoneLossLMList=indicatorMapper.queryCompanyIndicData(indicatorDTO);
				}
				else {
					indZoneLossLMList=indicatorMapper.queryZoneLossIndicData(indicatorDTO);	
				}
			   
				//清空条件
			//	indicatorDTO.setCodes(new ArrayList<String>());
				
				//分区漏损数据 (上一年)
				List<String> zoneLossLYList=new ArrayList<>();
				if(areaType==0) { //全网
				//查询上一年的年产销差率			
				zoneLossLYList.add(Constant.ZONE_LOSS_INDIC_WNYNRR);	
				//查询上一年的年漏损差率
				zoneLossLYList.add(Constant.ZONE_LOSS_INDIC_WNYMNFTDF);
				}else if(areaType==1) { //一级分区	
				//查询上一年的年产销差率			
				zoneLossLYList.add(Constant.ZONE_LOSS_INDIC_FLYNRR);	
				//查询上一年的年漏损差率
				zoneLossLYList.add(Constant.ZONE_LOSS_INDIC_FLYMNFTDF);	
				}else if(areaType==2) {  //二级分区
				//查询上一年的年产销差率			
				zoneLossLYList.add(Constant.ZONE_LOSS_INDIC_SLYNRR);	
				//查询上一年的年漏损差率
				zoneLossLYList.add(Constant.ZONE_LOSS_INDIC_SLYMNFTDF);
				}else {  //DMA
				//查询上一年的年产销差率			
				zoneLossLYList.add(Constant.ZONE_LOSS_INDIC_DMYNRR);	
				//查询上一年的年漏损差率
				zoneLossLYList.add(Constant.ZONE_LOSS_INDIC_DMYMNFTDF);	
				}
				//设置查询条件
				indicatorDTO.setStartTime(lastYearStartTime);
				indicatorDTO.setEndTime(lastYearEndTime);
				indicatorDTO.setCodes(zoneLossLYList);
				indicatorDTO.setTimeType(timeTypeYear);
				
				if(areaType==0) {
					indZoneLossLYList=indicatorMapper.queryCompanyIndicData(indicatorDTO);
				}
				else {
					indZoneLossLYList=indicatorMapper.queryZoneLossIndicData(indicatorDTO);	
				}
				
				
				//取出产销差率(当月)
				  double WNMNRRNow=0;
				  if(indZoneLossMList!=null && indZoneLossMList.size()>0) {
					  for(int inM=0;inM<indZoneLossMList.size();inM++) {
						 if(areaType==0) {  
						  if(Constant.ZONE_LOSS_INDIC_WNMNRR.equals(indZoneLossMList.get(inM).getCode())) {
							  WNMNRRNow=indZoneLossMList.get(inM).getValue();	
							  break;
						  }
						 }else if(areaType==1) {
						  if(Constant.ZONE_LOSS_INDIC_FLMNRR.equals(indZoneLossMList.get(inM).getCode())) {
								  WNMNRRNow=indZoneLossMList.get(inM).getValue();	
								  break;
						  } 
						 }else if(areaType==2) {
						  if(Constant.ZONE_LOSS_INDIC_SLMNRR.equals(indZoneLossMList.get(inM).getCode())) {
								  WNMNRRNow=indZoneLossMList.get(inM).getValue();	
								  break;
						  }  
						 }else {
						  if(Constant.ZONE_LOSS_INDIC_DMMNRR.equals(indZoneLossMList.get(inM).getCode())) {
								  WNMNRRNow=indZoneLossMList.get(inM).getValue();
								  break;
						  }   
						 }
					  }
				  }
				//取出产销差率(上个月)
				  double WNMNRRLast=0;
				  if(indZoneLossLMList!=null && indZoneLossLMList.size()>0) {
					  for(int inLM=0;inLM<indZoneLossLMList.size();inLM++) {
						 if(areaType==0) { 
						  if(Constant.ZONE_LOSS_INDIC_WNMNRR.equals(indZoneLossLMList.get(inLM).getCode())) {
							  WNMNRRLast=indZoneLossLMList.get(inLM).getValue();
						  }
						 }else if(areaType==1) {
						   if(Constant.ZONE_LOSS_INDIC_FLMNRR.equals(indZoneLossLMList.get(inLM).getCode())) {
							 WNMNRRLast=indZoneLossLMList.get(inLM).getValue();
						   } 
						 }else if(areaType==2) {
							 if(Constant.ZONE_LOSS_INDIC_SLMNRR.equals(indZoneLossLMList.get(inLM).getCode())) {
								 WNMNRRLast=indZoneLossLMList.get(inLM).getValue();
							 } 
						 }else {
							 if(Constant.ZONE_LOSS_INDIC_DMMNRR.equals(indZoneLossLMList.get(inLM).getCode())) {
								 WNMNRRLast=indZoneLossLMList.get(inLM).getValue();
							 }  
						 }
					  }
				  }
				  //返回计算结果bean
				  double finalWNMNRR=WNMNRRNow-WNMNRRLast;
				  IndicatorVO indicatorWNMNRR=new IndicatorVO();
				  if(areaType==0) {
				  indicatorWNMNRR.setCode(Constant.ZONE_LOSS_INDIC_WNMNRR);
				  }else if(areaType==1) {
				   indicatorWNMNRR.setCode(Constant.ZONE_LOSS_INDIC_FLMNRR);  
				  }else if(areaType==2) {
				   indicatorWNMNRR.setCode(Constant.ZONE_LOSS_INDIC_SLMNRR);   
				  }else {
				   indicatorWNMNRR.setCode(Constant.ZONE_LOSS_INDIC_DMMNRR);   
				  }
				  indicatorWNMNRR.setType("D5");
				  indicatorWNMNRR.setValue(finalWNMNRR);
				  indicatorWNMNRR.setTimeId(monthType);
				  finalList.add(indicatorWNMNRR);
				  
				//取出产销差率(当年)
				  double WNMNRRYNow=0;
				  if(indZoneLossYList!=null && indZoneLossYList.size()>0) {
					  for(int inM=0;inM<indZoneLossYList.size();inM++) {
						 if(areaType==0) {
						  if(Constant.ZONE_LOSS_INDIC_WNYNRR.equals(indZoneLossYList.get(inM).getCode())) {
							  WNMNRRYNow=indZoneLossYList.get(inM).getValue();
							  break;
						  }
						 }else if(areaType==1) {
						   if(Constant.ZONE_LOSS_INDIC_FLYNRR.equals(indZoneLossYList.get(inM).getCode())) {
							  WNMNRRYNow=indZoneLossYList.get(inM).getValue();	
							  break;
						   } 
						 }else if(areaType==2) {
						   if(Constant.ZONE_LOSS_INDIC_SLYNRR.equals(indZoneLossYList.get(inM).getCode())) {
								  WNMNRRYNow=indZoneLossYList.get(inM).getValue();	
								  break;
						   }  
						 }else {
							 if(Constant.ZONE_LOSS_INDIC_DMYNRR.equals(indZoneLossYList.get(inM).getCode())) {
								  WNMNRRYNow=indZoneLossYList.get(inM).getValue();	
								  break;
						   }  
						 }
					  }
				  }
				//取出产销差率(上个年)
				  double WNMNRRYLast=0;
				  if(indZoneLossLYList!=null && indZoneLossLYList.size()>0) {
				  for(int inLM=0;inLM<indZoneLossLYList.size();inLM++) {
					 if(areaType==0) {
					  if(Constant.ZONE_LOSS_INDIC_WNYNRR.equals(indZoneLossLYList.get(inLM).getCode())) {
						  WNMNRRYLast=indZoneLossLYList.get(inLM).getValue();
						  break;
					  }
					 }else if(areaType==1) {
					  if(Constant.ZONE_LOSS_INDIC_FLYNRR.equals(indZoneLossLYList.get(inLM).getCode())) {
						  WNMNRRYLast=indZoneLossLYList.get(inLM).getValue();
						  break;
						} 
					 }else if(areaType==2) {
					   if(Constant.ZONE_LOSS_INDIC_SLYNRR.equals(indZoneLossLYList.get(inLM).getCode())) {
						   WNMNRRYLast=indZoneLossLYList.get(inLM).getValue();
						   break;
					   } 
					 }else {
					   if(Constant.ZONE_LOSS_INDIC_DMYNRR.equals(indZoneLossLYList.get(inLM).getCode())) {
						   WNMNRRYLast=indZoneLossLYList.get(inLM).getValue();
						   break;
					   }  
					 }
				  }
				 }
				  //返回计算结果bean
				  double finalYWNMNRR=WNMNRRYNow-WNMNRRYLast;
				  IndicatorVO indicatorYWNMNRR=new IndicatorVO();
				  if(areaType==0) {
				  indicatorYWNMNRR.setCode(Constant.ZONE_LOSS_INDIC_WNYNRR);
				  }else if(areaType==1) {
				   indicatorYWNMNRR.setCode(Constant.ZONE_LOSS_INDIC_FLYNRR);  
				  }else if(areaType==2) {
				   indicatorYWNMNRR.setCode(Constant.ZONE_LOSS_INDIC_SLYNRR);  
				  }else {
					indicatorYWNMNRR.setCode(Constant.ZONE_LOSS_INDIC_DMYNRR);   
				  }
				  indicatorYWNMNRR.setType("D6");
				  indicatorYWNMNRR.setValue(finalYWNMNRR);
				  indicatorYWNMNRR.setTimeId(yearType);
				  finalList.add(indicatorYWNMNRR);
				  
				    //取出漏损率(当月)
					  double WNMMNFTDFNow=0;
					  if(indZoneLossMList!=null && indZoneLossMList.size()>0) {
						  for(int inM=0;inM<indZoneLossMList.size();inM++) {
							  if(areaType==0) {
								  if(Constant.ZONE_LOSS_INDIC_WNMMNFTDF.equals(indZoneLossMList.get(inM).getCode())) {
									  WNMMNFTDFNow=indZoneLossMList.get(inM).getValue();
									  break;
									  
								  }
							  }
							  else if(areaType==1) {
								  if(Constant.ZONE_LOSS_INDIC_FLMMNFTDF.equals(indZoneLossMList.get(inM).getCode())) {
									  WNMMNFTDFNow=indZoneLossMList.get(inM).getValue();
									  break;
									  
								  }
							  }
							  else if(areaType==2) {
								  if(Constant.ZONE_LOSS_INDIC_SLMMNFTDF.equals(indZoneLossMList.get(inM).getCode())) {
									  WNMMNFTDFNow=indZoneLossMList.get(inM).getValue();
									  break;
									  
								  }
							  }
							  else {
								  if(Constant.ZONE_LOSS_INDIC_DMMMNFTDF.equals(indZoneLossMList.get(inM).getCode())) {
									  WNMMNFTDFNow=indZoneLossMList.get(inM).getValue();
									  break;
									  
								  }
							  }
						  }
					  }
					//取出漏损率(上个月)
					  double WNMMNFTDFLast=0;
					  if(indZoneLossLMList!=null && indZoneLossLMList.size()>0) {
						  for(int inLM=0;inLM<indZoneLossLMList.size();inLM++) {
							if(areaType==0) {  
							  if(Constant.ZONE_LOSS_INDIC_WNMMNFTDF.equals(indZoneLossLMList.get(inLM).getCode())) {
								  WNMMNFTDFLast=indZoneLossLMList.get(inLM).getValue();
								  break;
							  }
							}else if(areaType==1) {
								if(Constant.ZONE_LOSS_INDIC_FLMMNFTDF.equals(indZoneLossLMList.get(inLM).getCode())) {
								  WNMMNFTDFLast=indZoneLossLMList.get(inLM).getValue();
								  break;
								 }	
							}else if(areaType==2) {
								if(Constant.ZONE_LOSS_INDIC_SLMMNFTDF.equals(indZoneLossLMList.get(inLM).getCode())) {
									 WNMMNFTDFLast=indZoneLossLMList.get(inLM).getValue();
									 break;
								 }	
							}else {
								if(Constant.ZONE_LOSS_INDIC_DMMMNFTDF.equals(indZoneLossLMList.get(inLM).getCode())) {
									  WNMMNFTDFLast=indZoneLossLMList.get(inLM).getValue();
									  break;
								 }	
							}
						  }
					  }
					  //返回计算结果bean
					  double finalWNMMNFTDF=WNMMNFTDFNow-WNMMNFTDFLast;
					  IndicatorVO indicatorWNMMNFTDF=new IndicatorVO();
					  if(areaType==0) {
					  indicatorWNMMNFTDF.setCode(Constant.ZONE_LOSS_INDIC_WNMMNFTDF);
					  }else if(areaType==1) {
					    indicatorWNMMNFTDF.setCode(Constant.ZONE_LOSS_INDIC_FLMMNFTDF);  
					  }else if(areaType==2) {
						indicatorWNMMNFTDF.setCode(Constant.ZONE_LOSS_INDIC_SLMMNFTDF);   
					  }else {
					    indicatorWNMMNFTDF.setCode(Constant.ZONE_LOSS_INDIC_DMMMNFTDF);   
					  }
					  indicatorWNMMNFTDF.setType("E5");
					  indicatorWNMMNFTDF.setValue(finalWNMMNFTDF);
					  indicatorWNMMNFTDF.setTimeId(monthType);
					  finalList.add(indicatorWNMMNFTDF);
					  
					  
				     //取出漏损率（当年）
					  double WNMMNFTDFYNow=0;
					  if(indZoneLossYList!=null && indZoneLossYList.size()>0) { 
						  for(int inM=0;inM<indZoneLossYList.size();inM++) {
							 if(areaType==0) {
							  if(Constant.ZONE_LOSS_INDIC_WNYMNFTDF.equals(indZoneLossYList.get(inM).getCode())) {
								  WNMMNFTDFYNow=indZoneLossYList.get(inM).getValue();
								  break;
							  }
							 }
							 else if(areaType==1) {
							   if(Constant.ZONE_LOSS_INDIC_FLYMNFTDF.equals(indZoneLossYList.get(inM).getCode())) {
									  WNMMNFTDFYNow=indZoneLossYList.get(inM).getValue();
									  break;
							  } 
							 }else if( areaType==2) {
								 if(Constant.ZONE_LOSS_INDIC_FLYMNFTDF.equals(indZoneLossYList.get(inM).getCode())) {
									  WNMMNFTDFYNow=indZoneLossYList.get(inM).getValue();	
									  break;
							  } 
							 }else {
								 if(Constant.ZONE_LOSS_INDIC_FLYMNFTDF.equals(indZoneLossYList.get(inM).getCode())) {
									  WNMMNFTDFYNow=indZoneLossYList.get(inM).getValue();
									  break;
							  } 	 
							 }
						   }
					  }
					//取出漏损率(上个年)
					  double WNMMNFTDFYLast=0;
					  if(indZoneLossLYList!=null && indZoneLossLYList.size()>0) {
						  for(int inLM=0;inLM<indZoneLossLMList.size();inLM++) {
							 if(areaType==0) { 
							  if(Constant.ZONE_LOSS_INDIC_WNYMNFTDF.equals(indZoneLossLYList.get(inLM).getCode())) {
								  WNMMNFTDFYLast=indZoneLossLYList.get(inLM).getValue();
								  break;
							  }
							 } else if(areaType==1) {
							  if(Constant.ZONE_LOSS_INDIC_FLYMNFTDF.equals(indZoneLossLYList.get(inLM).getCode())) {
								  WNMMNFTDFYLast=indZoneLossLYList.get(inLM).getValue();
								  break;
							  } 
							 }else if( areaType==2) {
							  if(Constant.ZONE_LOSS_INDIC_SLYMNFTDF.equals(indZoneLossLYList.get(inLM).getCode())) {
								  WNMMNFTDFYLast=indZoneLossLYList.get(inLM).getValue();
								  break;
							  }
							 }else {
							  if(Constant.ZONE_LOSS_INDIC_DMYMNFTDF.equals(indZoneLossLYList.get(inLM).getCode())) {
								  WNMMNFTDFYLast=indZoneLossLYList.get(inLM).getValue();
								  break;
							  } 
							 }
						  }
					  }
					  //返回计算结果bean
					  double finalYWNMMNFTDF=WNMMNFTDFYNow-WNMMNFTDFYLast;
					  IndicatorVO indicatorYWNMMNFTDF=new IndicatorVO();
					  if(areaType==0) {
					  indicatorYWNMMNFTDF.setCode(Constant.ZONE_LOSS_INDIC_WNYMNFTDF);
					  }else if(areaType==1) {
					  indicatorYWNMMNFTDF.setCode(Constant.ZONE_LOSS_INDIC_FLYMNFTDF);	 
					  }else if( areaType==2) {
				       indicatorYWNMMNFTDF.setCode(Constant.ZONE_LOSS_INDIC_SLYMNFTDF);	
					  }else {
				       indicatorYWNMMNFTDF.setCode(Constant.ZONE_LOSS_INDIC_DMYMNFTDF);	 
					  }
					  indicatorYWNMMNFTDF.setType("E6");
					  indicatorYWNMMNFTDF.setValue(finalYWNMMNFTDF);
					  indicatorYWNMMNFTDF.setTimeId(yearType);
					  finalList.add(indicatorYWNMMNFTDF);
				   
			finalList.addAll(indBaseIndicMList);
			finalList.addAll(indBalanceMList);
			finalList.addAll(indBalanceYList);
			finalList.addAll(indZoneLossMList);
			finalList.addAll(indZoneLossYList);
			finalList.addAll(indZoneLossLYList);
			finalList.addAll(indZoneLossLMList);
			return finalList;	
		}
	
    //查询时间段的指标值
	@TaskAnnotation("queryComYearInfo")
	@Override
	public MultParamterIndicatorVO queryComYearInfo(SessionFactory factory, IndicatorNewDTO indicatorDTO) {
		IndexMapper indicatorMapper = factory.getMapper(IndexMapper.class);
		//获取中间开始的年月
		String startLastTime=Integer.valueOf(indicatorDTO.getStartTime().toString().substring(0, 4))-2+"";
		String startMidTime=Integer.valueOf(indicatorDTO.getStartTime().toString().substring(0, 4))-1+"";
		String startMidDate=indicatorDTO.getStartTime().toString().substring(4, 6);
		//组成条件
		Integer lastStartTime=Integer.valueOf(startLastTime+startMidDate);
		Integer midTime=Integer.valueOf(startMidTime+startMidDate);		
		Integer nowEndTime=indicatorDTO.getEndTime();
		//判断是什么类型，全网，一级分区。。。
		int areaType=indicatorDTO.getAreaType();
		/**
		管网长度  用户数              ------基础指标-水司用户 BASE_INDIC
		供水量  月 年                    -------水平衡基础数据  BALANCE_INDIC
		抄表量  月 年                    -------水平衡基础数据  BALANCE_INDIC
		产销差  月 年                    -------水平衡基础数据  BALANCE_INDIC
		产销差率  月 年                 -------分区漏损数据   ZONE_LOSS_INDIC
		漏损量  年月                       -------表观漏损数据  APPARENT_INDIC
		漏损率  （年月）             -------分区漏损数据  ZONE_LOSS_INDIC
		单位户数MNF      --------分区漏损数据  
		单位管长                         --------分区漏损数据  
		*/
		MultParamterIndicatorVO multParamterIndicatorVO=new MultParamterIndicatorVO();
		double avgCurrentValue=0;
		double avgLastValue=0;
		 //设置查询方式月还是年指标
		indicatorDTO.setTimeType(timeTypeMonth);
		 //当年的
		 List<IndicatorVO> currentIndicatorList=new ArrayList<>();
		 //上年的
		 List<IndicatorVO> lastIndicatorList=new ArrayList<>();
		 
          List<String> codes=new ArrayList<>();
        
	     
		 if(areaType==0) { //全网	
			if(indicatorDTO.getType()==1) {
			  //供水量(月)
			  codes.add(Constant.BALANCE_INDIC_WNMFWSSITDF);		    
			}else if(indicatorDTO.getType()==2) {
			  //抄表量
			  codes.add(Constant.BALANCE_INDIC_WNMBMC);		
			}else if(indicatorDTO.getType()==3) {
			  //产销量	
			  codes.add(Constant.BALANCE_INDIC_WNMNRW);
			}else if(indicatorDTO.getType()==4) {
			  //单位户数mnf	
			  codes.add(Constant.ZONE_LOSS_INDIC_WNMLCA);
			}else {
			  //单位管长漏损量
			  codes.add(Constant.ZONE_LOSS_INDIC_WNMLPL);	
			}			
		 }else if(areaType==1) { //一级分区
		   if(indicatorDTO.getType()==1) {	 
		     //供水量(月)	 
		     codes.add(Constant.BALANCE_INDIC_FLMFWSSITDF);
		   }else if(indicatorDTO.getType()==2) {
			 //抄表量(月)
			 codes.add(Constant.BALANCE_INDIC_FLMBMC);	
		   }else if(indicatorDTO.getType()==3) {
			 //产销量	
			 codes.add(Constant.BALANCE_INDIC_FLMNRW); 
		   }else if(indicatorDTO.getType()==4) {
			 //单位户数mnf	
			 codes.add(Constant.ZONE_LOSS_INDIC_FLMLCA);
	    	}else {
	         //单位管长漏损量
			 codes.add(Constant.ZONE_LOSS_INDIC_FLMLPL);	
	    	}	
				
		 }else if(areaType==2) { //二级分区	
			if(indicatorDTO.getType()==1) {
		    //供水量(月)
			codes.add(Constant.BALANCE_INDIC_SLMFWSSITDF);
			}else if(indicatorDTO.getType()==2) {
			 //抄表量(月)
			 codes.add(Constant.BALANCE_INDIC_SLMBMC);	
			}else if(indicatorDTO.getType()==3) {
			  //产销量	
			  codes.add(Constant.BALANCE_INDIC_SLMNRW); 
			 }else if(indicatorDTO.getType()==4) {
			  //单位户数mnf	
			  codes.add(Constant.ZONE_LOSS_INDIC_SLMLCA); 
			 }else {
		        //单位管长漏损量
				codes.add(Constant.ZONE_LOSS_INDIC_SLMLPL);	
		     }
			  
		 }else { //DMA	
			 if(indicatorDTO.getType()==1) {
		      //供水量(月)
			  codes.add(Constant.BALANCE_INDIC_DMMFWSSITDF);
			 }else if(indicatorDTO.getType()==2) {
			  //抄表量(月)
			  codes.add(Constant.BALANCE_INDIC_DMMBMC);	
			 } else if(indicatorDTO.getType()==3) {
			  //产销量	
			  codes.add(Constant.BALANCE_INDIC_DMMNRW); 
		     }else if(indicatorDTO.getType()==4) {
			   //单位户数mnf	
			  codes.add(Constant.ZONE_LOSS_INDIC_DMMLCA); 
			 }else {
		       //单位管长漏损量
			  codes.add(Constant.ZONE_LOSS_INDIC_DMMLPL);	
		     }
		}	
		 indicatorDTO.setCodes(codes);	
			//当年的
		   indicatorDTO.setStartTime(midTime);
			indicatorDTO.setEndTime(nowEndTime);
			if(areaType==0) {
				currentIndicatorList=indicatorMapper.queryCompanyIndicData(indicatorDTO);
			}
			else if(indicatorDTO.getType()==1 || indicatorDTO.getType()==2 || indicatorDTO.getType()==3){
				currentIndicatorList=indicatorMapper.queryWBBaseIndicData(indicatorDTO);	
			}else{
				  currentIndicatorList=indicatorMapper.queryZoneLossIndicData(indicatorDTO);		
			}
			
			
			
			
			//上年的
			indicatorDTO.setStartTime(lastStartTime);
			indicatorDTO.setEndTime(midTime);
			if(areaType==0) {
				lastIndicatorList=indicatorMapper.queryCompanyIndicData(indicatorDTO);
			}
			else if(indicatorDTO.getType()==1 || indicatorDTO.getType()==2 || indicatorDTO.getType()==3){
				lastIndicatorList=indicatorMapper.queryWBBaseIndicData(indicatorDTO);
			}else{
				lastIndicatorList=indicatorMapper.queryZoneLossIndicData(indicatorDTO);		
			}
			
			
			
			double currentValue=0.0;
			double lastValue=0.0;
			//求当年的平均值
			if(currentIndicatorList!=null && currentIndicatorList.size()>0) {
				for(int i=0;i<currentIndicatorList.size();i++) {
					if(currentIndicatorList.get(i).getValue()==null) {
					    currentIndicatorList.get(i).setValue(0.0);
					}
					currentValue=currentValue+currentIndicatorList.get(i).getValue();
					
				}
				avgCurrentValue=currentValue/12;
			}else {
				avgCurrentValue=0;
			}
			//求上一年的平均值
			if(lastIndicatorList!=null && lastIndicatorList.size()>0) {
				for(int i=0;i<lastIndicatorList.size();i++) {
					if(lastIndicatorList.get(i).getValue()==null) {
						lastIndicatorList.get(i).setValue(0.0);
					}
					lastValue=lastValue+lastIndicatorList.get(i).getValue();
					
				}
				avgLastValue=lastValue/12;
			}else {
				avgLastValue=0;
			}
			multParamterIndicatorVO.setCurrentIndicatorList(currentIndicatorList);
			multParamterIndicatorVO.setLastIndicatorList(lastIndicatorList);
			multParamterIndicatorVO.setAvgCurrentValue(avgCurrentValue);
			multParamterIndicatorVO.setAvgLastValue(avgLastValue);
		
		
		return multParamterIndicatorVO;
	}

	//查询漏损任务相关信息接口 2020/04/28
	@TaskAnnotation("queryWarningInfo")
	@Override
	public InfoPageListVO<List<TaskMsgVO>> queryWarningInfo(SessionFactory factory, WarningInfoDTO warningInfoDTO) {
		// TODO Auto-generated method stub
		IndexMapper indicatorMapper = factory.getMapper(IndexMapper.class);
		//TODO查出所有分区的下级单位
		//查询任务列表
		List<TaskMsgVO> taskMsgList=indicatorMapper.queryTaskList(warningInfoDTO);
		//查询列表数量
		Integer rowNumber = indicatorMapper.queryTaskListNum(warningInfoDTO);
		//TODO  状态从数据字典查出
		
		double completeRate=0.0;
		double unCompleteRate=0.0;
		double compleingRate=0.0;
		double inTimeRate=0.0;
		String completeRateStr="0%";
		String unCompleteRateStr="0%";
		String compleingRateStr="0%";
		String inTimeRateStr="0%";
		
		double completeNum=0.0;
		DecimalFormat df = new DecimalFormat("0.0000%");
		//查询完成条数
		warningInfoDTO.setState("L101990003");
		List<InfoCompleteRateVO> completeList=indicatorMapper.queryComRateNum(warningInfoDTO);	
		if(completeList!=null && completeList.size()>0 && rowNumber!=null && rowNumber!=0) {
			completeNum=completeList.get(0).getCompleNum().longValue()*1.0;
			completeRate=completeList.get(0).getCompleNum().longValue()*1.0/rowNumber.longValue();		
			completeRateStr= df.format(completeRate);			
		}
		//查询未完成条数
		warningInfoDTO.setState("L101990001");
		List<InfoCompleteRateVO> unCompleteList=indicatorMapper.queryComRateNum(warningInfoDTO);
        if(unCompleteList!=null && unCompleteList.size()>0 && rowNumber!=null && rowNumber!=0) {
        	unCompleteRate=unCompleteList.get(0).getCompleNum().longValue()*1.0/rowNumber.longValue();		
        	unCompleteRateStr= df.format(unCompleteRate);	
		}
		//进行中的条数
		warningInfoDTO.setState("L101990002");
		List<InfoCompleteRateVO> completeingList=indicatorMapper.queryComRateNum(warningInfoDTO);
         if(completeingList!=null && completeingList.size()>0 && rowNumber!=null && rowNumber!=0) {
        	 compleingRate=completeingList.get(0).getCompleNum().longValue()*1.0/rowNumber.longValue();
        	 compleingRateStr=df.format(compleingRate);	
		} 
         //查询预计完成时间大于实际完成时间的条数
         Integer actualNum= indicatorMapper.queryActualTaskListNum(warningInfoDTO);
         if(actualNum!=null &&  completeNum!=0) {
         inTimeRate=actualNum.longValue()*1.0/new Double(completeNum).longValue();
         }
         inTimeRateStr=df.format(inTimeRate);
          
		InfoPageListVO<List<TaskMsgVO>> result = new InfoPageListVO<>();
		result.setDataList(taskMsgList);
		// 插入分页信息
		PageVO pageVO = PageUtil.getPageBean(warningInfoDTO.getPage(), warningInfoDTO.getPageCount(), rowNumber.intValue());
		result.setTotalPage(pageVO.getTotalPage());
		result.setRowNumber(pageVO.getRowNumber());
		result.setPageCount(pageVO.getPageCount());
		result.setPage(pageVO.getPage());
		result.setCompleteRate(completeRateStr);
		result.setUnCompleteRate(unCompleteRateStr);
		result.setCompleteingRate(compleingRateStr);
		result.setInTimeRate(inTimeRateStr);
		return result;
	}
	
	    //查询漏损任务相关信息接口 2020/04/28
		@TaskAnnotation("queryCheckWarningInfo")
		@Override
		public InfoPageListVO<List<TaskMsgVO>> queryCheckWarningInfo(SessionFactory factory, WarningInfoDTO warningInfoDTO) {
			IndexMapper indicatorMapper = factory.getMapper(IndexMapper.class);
			//TODO查出所有分区的下级单位
			
			//TODO 查询离线报警类别(数据字典)
			
			//查询报警类型详细信息
			List<TaskMsgVO>  taskMsgList=indicatorMapper.queryCheckWarningMsg(warningInfoDTO);
			
			double voiceWarningRate=0.0;
			double overWaningRate=0.0;
			double offlineWarningRate=0.0;
			double actualNumRate=0.0;
			DecimalFormat df = new DecimalFormat("0.0000%");
			
			String voiceWarningRateStr="0.000%";
			String overWaningRateStr="0.000%";
			String offlineWarningRateStr="0.000%";
			String actualNumRateStr="0.00%";
			//根据分区日期查询有多少个监测点报警的信息(离线总数)
			Integer checkWarningNum=indicatorMapper.queryCheckWarningNum(warningInfoDTO);
			//查询噪声报警的个数
			warningInfoDTO.setAlarmType("L102010005");
			Integer voiceWarningNum=indicatorMapper.queryTypeWarningNum(warningInfoDTO);
			//查询超限报警个数
			warningInfoDTO.setAlarmType("L102010002");
			Integer overWaningNum=indicatorMapper.queryTypeWarningNum(warningInfoDTO);
			//查询离线报警个数
			warningInfoDTO.setAlarmType("L102010004");
			Integer offlineWarningNum=indicatorMapper.queryTypeWarningNum(warningInfoDTO);
			
			//计算检测点任务完成个数
			 Integer actualNum= indicatorMapper.queryProActualTaskListNum(warningInfoDTO);
			 //计算监测点任务总个数
			 Integer actualTaskNum= indicatorMapper.queryProTaskListNum(warningInfoDTO);
			 if(actualNum!=null && actualTaskNum!=null && actualTaskNum!=0) {
				 actualNumRate=checkWarningNum.longValue()*1.0/actualTaskNum.longValue();
				 actualNumRateStr= df.format(actualNumRate);	
		     } 
			
			if(checkWarningNum!=null && checkWarningNum!=null && checkWarningNum!=0) {
				voiceWarningRate=voiceWarningNum.longValue()*1.0/checkWarningNum.longValue();
				voiceWarningRateStr= df.format(voiceWarningRate);	
			}if(voiceWarningNum!=null && checkWarningNum!=null && checkWarningNum!=0) {
				overWaningRate=overWaningNum.longValue()*1.0/checkWarningNum.longValue();
				overWaningRateStr= df.format(overWaningRate);
			}if(offlineWarningNum!=null && checkWarningNum!=null && checkWarningNum!=0) {
				offlineWarningRate=offlineWarningNum.longValue()*1.0/checkWarningNum.longValue();
				offlineWarningRateStr= df.format(offlineWarningRate);
			}
			
			InfoPageListVO<List<TaskMsgVO>> result = new InfoPageListVO<>();
			result.setDataList(taskMsgList);
			// 插入分页信息
			PageVO pageVO = PageUtil.getPageBean(warningInfoDTO.getPage(), warningInfoDTO.getPageCount(), checkWarningNum.intValue());
			result.setTotalPage(pageVO.getTotalPage());
			result.setRowNumber(pageVO.getRowNumber());
			result.setPageCount(pageVO.getPageCount());
			result.setPage(pageVO.getPage());
			result.setVoiceWarningRateStr(voiceWarningRateStr);
			result.setOfflineWarningRateStr(offlineWarningRateStr);
			result.setOverWaningRateStr(overWaningRateStr);
			result.setInTimeRate(actualNumRateStr);
			return result;
		}
		
		//查询分区排名  2020-04-29
		@TaskAnnotation("queryAreaRankInfo")
		@Override
		public List<IndicatorVO>  queryAreaRankInfo(SessionFactory factory, IndicatorNewDTO indicatorDTO) {
			IndexMapper indicatorMapper = factory.getMapper(IndexMapper.class);
			//判断是什么类型，一级分区还是二级分区	
			int areaType = indicatorDTO.getAreaType()+1;
			/**
			管网长度  用户数              ------基础指标-水司用户 BASE_INDIC
			供水量  月 年                    -------水平衡基础数据  BALANCE_INDIC
			抄表量  月 年                    -------水平衡基础数据  BALANCE_INDIC
			产销差  月 年                    -------水平衡基础数据  BALANCE_INDIC
			产销差率  月 年                 -------分区漏损数据   ZONE_LOSS_INDIC
			漏损量  年月                       -------表观漏损数据  APPARENT_INDIC
			漏损率  （年月）             -------分区漏损数据  ZONE_LOSS_INDIC
			单位户数MNF      --------分区漏损数据  
			单位管长                         --------分区漏损数据  
			*/
			
					
			 List<String> codes=new ArrayList<>();
			 if(areaType==0) { //全网	
				if(indicatorDTO.getType()==1) {
				  //供水量(月)
				  codes.add(Constant.BALANCE_INDIC_WNMFWSSITDF);		    
				}else if(indicatorDTO.getType()==2) {
				  //抄表量
				  codes.add(Constant.BALANCE_INDIC_WNMBMC);		
				}else if(indicatorDTO.getType()==3) {
				  //产销量	
				  codes.add(Constant.BALANCE_INDIC_WNMNRW);
				}else if(indicatorDTO.getType()==4) {
				  //单位户数mnf	
				  codes.add(Constant.ZONE_LOSS_INDIC_WNMLCA);
				}else {
				  //单位管长漏损量
				  codes.add(Constant.ZONE_LOSS_INDIC_WNMLPL);	
				}			
			 }else if(areaType==1) { //一级分区
			   if(indicatorDTO.getType()==1) {	 
			     //供水量(月)	 
			     codes.add(Constant.BALANCE_INDIC_FLMFWSSITDF);
			   }else if(indicatorDTO.getType()==2) {
				 //抄表量(月)
				 codes.add(Constant.BALANCE_INDIC_FLMBMC);	
			   }else if(indicatorDTO.getType()==3) {
				 //产销量	
				 codes.add(Constant.BALANCE_INDIC_FLMNRW); 
			   }else if(indicatorDTO.getType()==4) {
				 //单位户数mnf	
				 codes.add(Constant.ZONE_LOSS_INDIC_FLMLCA);
		    	}else {
		         //单位管长漏损量
				 codes.add(Constant.ZONE_LOSS_INDIC_FLMLPL);	
		    	}	
					
			 }else if(areaType==2) { //二级分区	
				if(indicatorDTO.getType()==1) {
			    //供水量(月)
				codes.add(Constant.BALANCE_INDIC_SLMFWSSITDF);
				}else if(indicatorDTO.getType()==2) {
				 //抄表量(月)
				 codes.add(Constant.BALANCE_INDIC_SLMBMC);	
				}else if(indicatorDTO.getType()==3) {
				  //产销量	
				  codes.add(Constant.BALANCE_INDIC_SLMNRW); 
				 }else if(indicatorDTO.getType()==4) {
				  //单位户数mnf	
				  codes.add(Constant.ZONE_LOSS_INDIC_SLMLCA); 
				 }else {
			        //单位管长漏损量
					codes.add(Constant.ZONE_LOSS_INDIC_SLMLPL);	
			     }
				  
			 }else { //DMA	
				 if(indicatorDTO.getType()==1) {
			      //供水量(月)
				  codes.add(Constant.BALANCE_INDIC_DMMFWSSITDF);
				 }else if(indicatorDTO.getType()==2) {
				  //抄表量(月)
				  codes.add(Constant.BALANCE_INDIC_DMMBMC);	
				 } else if(indicatorDTO.getType()==3) {
				  //产销量	
				  codes.add(Constant.BALANCE_INDIC_DMMNRW); 
			     }else if(indicatorDTO.getType()==4) {
				   //单位户数mnf	
				  codes.add(Constant.ZONE_LOSS_INDIC_DMMLCA); 
				 }else {
			       //单位管长漏损量
				  codes.add(Constant.ZONE_LOSS_INDIC_DMMLPL);	
			     }
			}	
			 indicatorDTO.setCodes(codes);
			 indicatorDTO.setTimeType(timeTypeMonth);
			//查询数据
			 List<IndicatorVO> currentIndicatorList=new ArrayList<>();
			 if(areaType == 1) {
				 if(indicatorDTO.getType()==1 || indicatorDTO.getType()==2 || indicatorDTO.getType()==3) {
					 currentIndicatorList=indicatorMapper.queryWBBaseIndicData(indicatorDTO);	
				 }else{
					 currentIndicatorList=indicatorMapper.queryZoneLossIndicData(indicatorDTO);		
				}
			 }else {
				 int type = 2;
				 String foreignKey = indicatorDTO.getZoneCodes().get(0);
				 TreeMapper mapper = factory.getMapper(TreeMapper.class);	
				 LongTreeBean node=mapper.getBeanByForeignIdType(type,foreignKey);
				 if(node == null) {
					 currentIndicatorList = null;
				 }
				 else{
					List<TreeZoneVO> zoneList=indicatorMapper.queryAllZone(node.getSeq(),node.getType(),node.getMask(),node.getParentMask());
					List<String> zoneNo=new ArrayList<>();
					for(int i = 0;i < zoneList.size(); i++) {
						zoneNo.add(zoneList.get(i).getCode());
					}
					indicatorDTO.setZoneCodes(zoneNo);
					currentIndicatorList=indicatorMapper.queryWBBaseIndicData(indicatorDTO);	
				 }
				 
			 }
			 
			return currentIndicatorList;
		}
		
		//查询子分区排名  2020-06-11
		@TaskAnnotation("queryChildAreaRankInfo")
		@Override
		public List<IndicatorVO>  queryChildAreaRankInfo(SessionFactory factory, IndicatorNewDTO indicatorDTO) {
			IndexMapper indicatorMapper = factory.getMapper(IndexMapper.class);
			//判断是什么类型，一级分区还是二级分区	
			int areaType = indicatorDTO.getAreaType()+2;
			/**
			管网长度  用户数              ------基础指标-水司用户 BASE_INDIC
			供水量  月 年                    -------水平衡基础数据  BALANCE_INDIC
			抄表量  月 年                    -------水平衡基础数据  BALANCE_INDIC
			产销差  月 年                    -------水平衡基础数据  BALANCE_INDIC
			产销差率  月 年                 -------分区漏损数据   ZONE_LOSS_INDIC
			漏损量  年月                       -------表观漏损数据  APPARENT_INDIC
			漏损率  （年月）             -------分区漏损数据  ZONE_LOSS_INDIC
			单位户数MNF      --------分区漏损数据  
			单位管长                         --------分区漏损数据  
			*/
			
					
			 List<String> codes=new ArrayList<>();
			 if(areaType==0) { //全网	
				if(indicatorDTO.getType()==1) {
				  //供水量(月)
				  codes.add(Constant.BALANCE_INDIC_WNMFWSSITDF);		    
				}else if(indicatorDTO.getType()==2) {
				  //抄表量
				  codes.add(Constant.BALANCE_INDIC_WNMBMC);		
				}else if(indicatorDTO.getType()==3) {
				  //产销量	
				  codes.add(Constant.BALANCE_INDIC_WNMNRW);
				}else if(indicatorDTO.getType()==4) {
				  //单位户数mnf	
				  codes.add(Constant.ZONE_LOSS_INDIC_WNMLCA);
				}else {
				  //单位管长漏损量
				  codes.add(Constant.ZONE_LOSS_INDIC_WNMLPL);	
				}			
			 }else if(areaType==1) { //一级分区
			   if(indicatorDTO.getType()==1) {	 
			     //供水量(月)	 
			     codes.add(Constant.BALANCE_INDIC_FLMFWSSITDF);
			   }else if(indicatorDTO.getType()==2) {
				 //抄表量(月)
				 codes.add(Constant.BALANCE_INDIC_FLMBMC);	
			   }else if(indicatorDTO.getType()==3) {
				 //产销量	
				 codes.add(Constant.BALANCE_INDIC_FLMNRW); 
			   }else if(indicatorDTO.getType()==4) {
				 //单位户数mnf	
				 codes.add(Constant.ZONE_LOSS_INDIC_FLMLCA);
		    	}else {
		         //单位管长漏损量
				 codes.add(Constant.ZONE_LOSS_INDIC_FLMLPL);	
		    	}	
					
			 }else if(areaType==2) { //二级分区	
				if(indicatorDTO.getType()==1) {
			    //供水量(月)
				codes.add(Constant.BALANCE_INDIC_SLMFWSSITDF);
				}else if(indicatorDTO.getType()==2) {
				 //抄表量(月)
				 codes.add(Constant.BALANCE_INDIC_SLMBMC);	
				}else if(indicatorDTO.getType()==3) {
				  //产销量	
				  codes.add(Constant.BALANCE_INDIC_SLMNRW); 
				 }else if(indicatorDTO.getType()==4) {
				  //单位户数mnf	
				  codes.add(Constant.ZONE_LOSS_INDIC_SLMLCA); 
				 }else {
			        //单位管长漏损量
					codes.add(Constant.ZONE_LOSS_INDIC_SLMLPL);	
			     }
				  
			 }else { //DMA	
				 if(indicatorDTO.getType()==1) {
			      //供水量(月)
				  codes.add(Constant.BALANCE_INDIC_DMMFWSSITDF);
				 }else if(indicatorDTO.getType()==2) {
				  //抄表量(月)
				  codes.add(Constant.BALANCE_INDIC_DMMBMC);	
				 } else if(indicatorDTO.getType()==3) {
				  //产销量	
				  codes.add(Constant.BALANCE_INDIC_DMMNRW); 
			     }else if(indicatorDTO.getType()==4) {
				   //单位户数mnf	
				  codes.add(Constant.ZONE_LOSS_INDIC_DMMLCA); 
				 }else {
			       //单位管长漏损量
				  codes.add(Constant.ZONE_LOSS_INDIC_DMMLPL);	
			     }
			}	
			 indicatorDTO.setCodes(codes);
			 indicatorDTO.setTimeType(timeTypeMonth);
			//查询数据
			 List<IndicatorVO> currentIndicatorList=new ArrayList<>();
			 if(areaType == 2) {
				 if(indicatorDTO.getType()==1 || indicatorDTO.getType()==2 || indicatorDTO.getType()==3) {
					 currentIndicatorList=indicatorMapper.queryWBBaseIndicData(indicatorDTO);	
				 }else{
					 currentIndicatorList=indicatorMapper.queryZoneLossIndicData(indicatorDTO);		
				}
			 }else {
				 int type = 2;
				 String foreignKey = indicatorDTO.getZoneCodes().get(0);
				 TreeMapper mapper = factory.getMapper(TreeMapper.class);	
				 LongTreeBean node=mapper.getBeanByForeignIdType(type,foreignKey);
				 if(node == null) {
					 currentIndicatorList = null;
				 }
				 else{
					List<TreeZoneVO> zoneList=indicatorMapper.queryAllZone(node.getSeq(),node.getType(),node.getMask(),node.getParentMask());
					List<String> zoneNo=new ArrayList<>();
					for(int i = 0;i < zoneList.size(); i++) {
						zoneNo.add(zoneList.get(i).getCode());
					}
					indicatorDTO.setZoneCodes(zoneNo);
					currentIndicatorList=indicatorMapper.queryWBBaseIndicData(indicatorDTO);	
				 }
				 
			 }
			 
			return currentIndicatorList;
		}
		
		
		
		/**
		 * 获取节点下所有子节点(菜单)
		 * @param factory
		 * @param type 类型
		 * @param seq 顺序值
		 * @param mask 掩码位数
		 * @param parentMask 父级掩码位数
		 * 
		 * @return
		 */
		@TaskAnnotation("queryAllZone")
		public List<TreeZoneVO> queryAllZone(SessionFactory factory,int type,String foreignKey){
			IndexMapper indicatorMapper = factory.getMapper(IndexMapper.class);
			TreeMapper mapper = factory.getMapper(TreeMapper.class);	
			LongTreeBean node=mapper.getBeanByForeignIdType(type,foreignKey);
			if(node == null) {
				return null;
			}
			else{
				List<TreeZoneVO> zoneList=indicatorMapper.queryAllZone(node.getSeq(),node.getType(),node.getMask(),node.getParentMask());
				return zoneList;
			}
		}
}

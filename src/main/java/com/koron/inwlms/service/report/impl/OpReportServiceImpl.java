package com.koron.inwlms.service.report.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koron.inwlms.bean.DTO.report.DmaOperabilityReportDTO;
import com.koron.inwlms.bean.DTO.report.LoggerFlowQuestionReportDTO;
import com.koron.inwlms.bean.DTO.report.PFLoggerListReportDTO;
import com.koron.inwlms.bean.DTO.report.PFLoggerOperabilityReportDTO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.common.PageVO;
import com.koron.inwlms.bean.VO.report.DmaOperabilityReportVO;
import com.koron.inwlms.bean.VO.report.LoggerFlowQuestionReportVO;
import com.koron.inwlms.bean.VO.report.NoiseLoggerListReportVO;
import com.koron.inwlms.bean.VO.report.PFLoggerDataInfo;
import com.koron.inwlms.bean.VO.report.PFLoggerListReportVO;
import com.koron.inwlms.bean.VO.report.PFLoggerOperabilityReportVO;
import com.koron.inwlms.mapper.report.OpReportMapper;
import com.koron.inwlms.service.report.OpReportService;
import com.koron.inwlms.util.ExportReportUtil;
import com.koron.inwlms.util.PageUtil;
import com.koron.inwlms.util.TimeUtil;


/**
 * 操作报表接口实现类
 * 
 * @author csh
 * @Date 2020/03/23
 *
 */
@Service
public class OpReportServiceImpl implements OpReportService {

	@TaskAnnotation("queryPFLoggerListReport")
	@Override
	public PageListVO<List<PFLoggerListReportVO>> queryPFLoggerListReport(SessionFactory factory,
			PFLoggerListReportDTO pFLoggerListReportDTO) {
		OpReportMapper mapper = factory.getMapper(OpReportMapper.class);
		List<PFLoggerListReportVO> lists = mapper.queryPFLoggerListReport(pFLoggerListReportDTO);
		PageListVO<List<PFLoggerListReportVO>> result = new PageListVO<>();
		result.setDataList(lists);
		Integer num = mapper.countPFLoggerListReport(pFLoggerListReportDTO);
		// 插入分页信息
		PageVO pageVO = PageUtil.getPageBean(pFLoggerListReportDTO.getPage(), pFLoggerListReportDTO.getPageCount(), num);
		result.setTotalPage(pageVO.getTotalPage());
		result.setRowNumber(pageVO.getRowNumber());
		result.setPageCount(pageVO.getPageCount());
		result.setPage(pageVO.getPage());
		return result;
	}

	@TaskAnnotation("downloadPFLoggerListReport")
	@Override
	public XSSFWorkbook downloadPFLoggerListReport(SessionFactory factory, Map<String, Object> map) {
		OpReportMapper mapper = factory.getMapper(OpReportMapper.class);
		PFLoggerListReportDTO pFLoggerListReportDTO = (PFLoggerListReportDTO) map.get("pfllrDTO");
        List<List<Map<String, Object>>> titleInfos = (List<List<Map<String, Object>>>) map.get("titleInfos");
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String[][] baseInfo = new String[2][2];
        baseInfo[0] = new String[]{"Report ID: OP-01", "Regional Scope: " + (StringUtils.isBlank(pFLoggerListReportDTO.getZoneNo()) ? "Whole Network" : pFLoggerListReportDTO.getZoneNo())};
        baseInfo[1] = new String[]{"Print Date: " + sf.format(new Date()), ""};
//        if (titleInfos == null) {
//            String titleString = "[[{\"titleName\":\"Logger Ref\",\"titleValue\":\"loggerRef\",\"colspan\":\"1\",\"rowspan\":\"1\"},{\"titleName\":\"Logger ID\",\"titleValue\":\"loggerId\",\"colspan\":\"1\",\"rowspan\":\"1\"},{\"titleName\":\"Related DMA No.\",\"titleValue\":\"dmaNos\",\"colspan\":\"1\",\"rowspan\":\"1\"},{\"titleName\":\"Region\",\"titleValue\":\"region\",\"colspan\":\"1\",\"rowspan\":\"1\"},{\"titleName\":\"Section\",\"titleValue\":\"subRegion\",\"colspan\":\"1\",\"rowspan\":\"1\"},{\"titleName\":\"Read Interval (min)\",\"titleValue\":\"readInterva\",\"colspan\":\"1\",\"rowspan\":\"1\"},{\"titleName\":\"Layer In DMS\",\"titleValue\":\"facType\",\"colspan\":\"1\",\"rowspan\":\"1\"},{\"titleName\":\"Facility ID\",\"titleValue\":\"facId\",\"colspan\":\"1\",\"rowspan\":\"1\"},{\"titleName\":\"Valid Facility ID\",\"titleValue\":\"isValid\",\"colspan\":\"1\",\"rowspan\":\"1\"},{\"titleName\":\"Coordinate X\",\"titleValue\":\"coordinateX\",\"colspan\":\"1\",\"rowspan\":\"1\"},{\"titleName\":\"Coordinate Y\",\"titleValue\":\"coordinateY\",\"colspan\":\"1\",\"rowspan\":\"1\"},{\"titleName\":\"Manufacturer\",\"titleValue\":\"manufacturer\",\"colspan\":\"1\",\"rowspan\":\"1\"}]]";
//            Gson jsonValue = new Gson();
//            titleInfos = jsonValue.fromJson(titleString, new TypeToken<List<List<Map<String, Object>>>>() {
//            }.getType());
//        }
        List<PFLoggerListReportVO> lists = mapper.queryPFLoggerListReport(pFLoggerListReportDTO);
		
        if (lists != null && lists.size() > 0) {
            for (PFLoggerListReportVO vo : lists) {
//                vo.setRegion(com.koron.inms.constant.Constant.SUB_DIC_MAP.get(com.koron.inms.constant.Constant.REGION).get(vo.getRegion()));
//                vo.setFacType(com.koron.inms.constant.Constant.SUB_DIC_MAP.get(com.koron.inms.constant.Constant.FW_ITEM_TYPE).get(vo.getFacType()));
//                vo.setManufacturer(com.koron.inms.constant.Constant.SUB_DIC_MAP.get(com.koron.inms.constant.Constant.MANUFACTURER).get(vo.getManufacturer()));
//                vo.setSubRegion(com.koron.inms.constant.Constant.SUB_DIC_MAP.get(com.koron.inms.constant.Constant.SUB_REGION).get(vo.getSubRegion()));
            }
        }
        return ExportReportUtil.export("List of Instrument Report (Flow And Pressure Logger)", baseInfo, titleInfos, lists, ExportReportUtil.NOT_MERGE_DATA_CELL, null, null);
   
	}

	@Override
	public PageListVO<List<NoiseLoggerListReportVO>> queryNoiseLoggerListReport(SessionFactory factory,
			PFLoggerListReportDTO pFLoggerListReportDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public XSSFWorkbook downloadNoiseLoggerListReport(SessionFactory factory, Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageListVO<List<DmaOperabilityReportVO>> queryDmaOperabilityReport(SessionFactory factory,
			DmaOperabilityReportDTO dmaOperabilityReportDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public XSSFWorkbook downloadDmaOperabilityReport(SessionFactory factory, Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@TaskAnnotation("queryPFLoggerOperabilityReport")
	@Override
	public PageListVO<List<PFLoggerOperabilityReportVO>> queryPFLoggerOperabilityReport(SessionFactory factory,
			PFLoggerOperabilityReportDTO pFLoggerOperabilityReportDTO) {
		PageListVO<List<PFLoggerOperabilityReportVO>> result = new PageListVO<>();
		OpReportMapper mapper = factory.getMapper(OpReportMapper.class);
        List<PFLoggerOperabilityReportVO> lists = mapper.queryPFLoggerOperabilityReport(pFLoggerOperabilityReportDTO);//启用状态的sis流量计
        List<PFLoggerDataInfo> datas = mapper.queryPFLoggerData(pFLoggerOperabilityReportDTO);//压力流量计历史检测数据
        List<PFLoggerDataInfo> lastRecordDatas = mapper.queryPFLoggerLastRecordTime();//sis流量计最后一天的记录日期
        List<String> totalDays = TimeUtil.getBetweenTime(pFLoggerOperabilityReportDTO.getStartTime(), pFLoggerOperabilityReportDTO.getEndTime());//两个时间之间的日期
        lists.forEach(logger -> {//循环监测点
            String loggerNo = logger.getLoggerNo();//当前监测点logger
            for (PFLoggerDataInfo lastRecordData : lastRecordDatas) {//遍历sis流量计最后一天的记录日期，找到当前sis流量计对应的日期
                if (loggerNo.equals(lastRecordData.getLoggerNo())) {
                	logger.setLastReadingTime(lastRecordData.getDate());
                    break;
                }
            }

            AtomicInteger operableCount = new AtomicInteger(0);
            datas.forEach(data -> {//流量监测点历史监测数据，并统计可操作性天数
                if (loggerNo.equals(data.getLoggerNo())) {
                    operableCount.incrementAndGet();
                }
            });
            logger.setTotalDays(String.valueOf(totalDays.size()));//所选时间段总共天数
            logger.setOperableDays(String.valueOf(operableCount));//可操作性天数
            DecimalFormat df = new DecimalFormat("#.0000");
    		double percent = Double.parseDouble(df.format(operableCount.get() / (totalDays.size() * 1.0)));
            logger.setOperablePercent(percent);//可操作性百分比
        });
        result.setDataList(lists);
        Integer num = mapper.countPFLoggerOperabilityReport(pFLoggerOperabilityReportDTO);
		// 插入分页信息
		PageVO pageVO = PageUtil.getPageBean(pFLoggerOperabilityReportDTO.getPage(), pFLoggerOperabilityReportDTO.getPageCount(), num);
		result.setTotalPage(pageVO.getTotalPage());
		result.setRowNumber(pageVO.getRowNumber());
		result.setPageCount(pageVO.getPageCount());
		result.setPage(pageVO.getPage());
		return result;
	}

	@TaskAnnotation("downloadPFLoggerOperabilityReport")
	@Override
	public XSSFWorkbook downloadPFLoggerOperabilityReport(SessionFactory factory, Map<String, Object> map) {
		PFLoggerOperabilityReportDTO pFLoggerOperabilityReportDTO = (PFLoggerOperabilityReportDTO) map.get("pflorDTO");
	        List<List<Map<String, Object>>> titleInfos = (List<List<Map<String, Object>>>) map.get("titleInfos");
	        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String scope;
	        scope = StringUtils.isBlank(pFLoggerOperabilityReportDTO.getZoneNo())?"Whole Network":pFLoggerOperabilityReportDTO.getZoneNo();
	        String[][] baseInfo = new String[3][2];
	        baseInfo[0] = new String[]{"Report ID: OP-04", "Regional Scope: " + scope};
	        baseInfo[1] = new String[]{"Print Date: " + sf.format(new Date()), "From: " + pFLoggerOperabilityReportDTO.getStartTime()};
	        baseInfo[2] = new String[]{"", "To: " + pFLoggerOperabilityReportDTO.getEndTime()};
//	        if (titleInfos == null) {
//	            String titleString = "[[{\"titleName\":\"Region\",\"titleValue\":\"region\",\"colspan\":\"1\",\"rowspan\":\"1\"},{\"titleName\":\"Current Logger ID\",\"titleValue\":\"meterId\",\"colspan\":\"1\",\"rowspan\":\"1\"},{\"titleName\":\"Logger Ref\",\"titleValue\":\"meterRef\",\"colspan\":\"1\",\"rowspan\":\"1\"},{\"titleName\":\"Related DMA No.\",\"titleValue\":\"areaRef\",\"colspan\":\"1\",\"rowspan\":\"1\"},{\"titleName\":\"Total Number of Days\",\"titleValue\":\"totalDays\",\"colspan\":\"1\",\"rowspan\":\"1\"},{\"titleName\":\"No. of Days Operable\",\"titleValue\":\"operableDays\",\"colspan\":\"1\",\"rowspan\":\"1\"},{\"titleName\":\"Operable (%)\",\"titleValue\":\"operablePercentName\",\"colspan\":\"1\",\"rowspan\":\"1\"},{\"titleName\":\"Lastest Timestamp\",\"titleValue\":\"lastReadingTime\",\"colspan\":\"1\",\"rowspan\":\"1\"}]]";
//	            Gson jsonValue = new Gson();
//	            titleInfos = jsonValue.fromJson(titleString, new TypeToken<List<List<Map<String, Object>>>>() {
//	            }.getType());
//	        }
			OpReportMapper mapper = factory.getMapper(OpReportMapper.class);
	        List<PFLoggerOperabilityReportVO> lists = mapper.queryPFLoggerOperabilityReport(pFLoggerOperabilityReportDTO);//启用状态的sis流量计
	        List<PFLoggerDataInfo> datas = mapper.queryPFLoggerData(pFLoggerOperabilityReportDTO);//压力流量计历史检测数据
	        List<PFLoggerDataInfo> lastRecordDatas = mapper.queryPFLoggerLastRecordTime();//sis流量计最后一天的记录日期
	        List<String> totalDays = TimeUtil.getBetweenTime(pFLoggerOperabilityReportDTO.getStartTime(), pFLoggerOperabilityReportDTO.getEndTime());//两个时间之间的日期
	        lists.forEach(logger -> {//循环监测点
	            String loggerNo = logger.getLoggerNo();//当前监测点logger
	            for (PFLoggerDataInfo lastRecordData : lastRecordDatas) {//遍历sis流量计最后一天的记录日期，找到当前sis流量计对应的日期
	                if (loggerNo.equals(lastRecordData.getLoggerNo())) {
	                	logger.setLastReadingTime(lastRecordData.getDate());
	                    break;
	                }
	            }

	            AtomicInteger operableCount = new AtomicInteger(0);
	            datas.forEach(data -> {//流量监测点历史监测数据，并统计可操作性天数
	                if (loggerNo.equals(data.getLoggerNo())) {
	                    operableCount.incrementAndGet();
	                }
	            });
	            logger.setTotalDays(String.valueOf(totalDays.size()));//所选时间段总共天数
	            logger.setOperableDays(String.valueOf(operableCount));//可操作性天数
	            DecimalFormat df = new DecimalFormat("#.0000");
	    		double percent = Double.parseDouble(df.format(operableCount.get() / (totalDays.size() * 1.0)));
	            logger.setOperablePercent(percent);//可操作性百分比
	        });
	        if (lists != null && lists.size() > 0) {
	            for (PFLoggerOperabilityReportVO vo : lists) {
//	                vo.setRegion(com.koron.inms.constant.Constant.SUB_DIC_MAP.get(com.koron.inms.constant.Constant.REGION).get(vo.getRegion()));
	            }
	        }
	        int count1 = 0;
	        int count2 = 0;
	        int count3 = 0;
	        int count4 = 0;
	        for (PFLoggerOperabilityReportVO list : lists) {
	        	if(list.getOperablePercent() == null) continue;
	            double operablePercent = list.getOperablePercent();
	            if (operablePercent >= 0 && operablePercent <= 0.20) {
	                ++count1;
	            } else if (operablePercent > 0.20 && operablePercent <= 0.50) {
	                ++count2;
	            } else if (operablePercent > 0.50 && operablePercent <= 0.80) {
	                ++count3;
	            } else {
	                ++count4;
	            }
	        }
	        String[][] pieDataList = new String[5][2];
	        pieDataList[0] = new String[]{"Operable%", "Number"};
	        pieDataList[1] = new String[]{"[0%, 20%]", String.valueOf(count1)};
	        pieDataList[2] = new String[]{"(20%, 50%]", String.valueOf(count2)};
	        pieDataList[3] = new String[]{"(50%, 80%]", String.valueOf(count3)};
	        pieDataList[4] = new String[]{"(80%, 100%]", String.valueOf(count4)};
	        return ExportReportUtil.export("Data Logger Operability Report", baseInfo, titleInfos, lists, ExportReportUtil.PIE_CHART, pieDataList, ExportReportUtil.NOT_MERGE_DATA_CELL, null, null);

	}

	/**
	 * 查询监测水量存疑报表
	 */
	@TaskAnnotation("queryLoggerFlowQuestionReport")
	public PageListVO<List<LoggerFlowQuestionReportVO>> queryLoggerFlowQuestionReport(SessionFactory factory,
			LoggerFlowQuestionReportDTO loggerFlowQuestionReportDTO) {
		PageListVO<List<LoggerFlowQuestionReportVO>> result = new PageListVO<>();
		OpReportMapper mapper = factory.getMapper(OpReportMapper.class);
		int days = 0;
		try {
			days = TimeUtil.dateBetween(loggerFlowQuestionReportDTO.getStartTime(), loggerFlowQuestionReportDTO.getEndTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<LoggerFlowQuestionReportVO> lists = mapper.queryLoggerFlowQuestionReport(loggerFlowQuestionReportDTO, days);
		for (LoggerFlowQuestionReportVO loggerFlowQuestionReportVO : lists) {
			int doubts = loggerFlowQuestionReportVO.getMissingData() != null?loggerFlowQuestionReportVO.getMissingData():0;
			doubts = loggerFlowQuestionReportVO.getFlow() != null?(doubts>loggerFlowQuestionReportVO.getFlow()?doubts:loggerFlowQuestionReportVO.getFlow()):doubts;
			doubts = loggerFlowQuestionReportVO.getMajor() != null?(doubts>loggerFlowQuestionReportVO.getMajor()?doubts:loggerFlowQuestionReportVO.getMajor()):doubts;
			doubts = loggerFlowQuestionReportVO.getAbnoraml() != null?(doubts>loggerFlowQuestionReportVO.getAbnoraml()?doubts:loggerFlowQuestionReportVO.getAbnoraml()):doubts;
			loggerFlowQuestionReportVO.setDays(days);
			loggerFlowQuestionReportVO.setDoubts(doubts);
			DecimalFormat df = new DecimalFormat("#.0000");
			loggerFlowQuestionReportVO.setRate(days == 0?0.0:Double.parseDouble(df.format((doubts*1.0)/(days*1.0))));
		}
		result.setDataList(lists);
		Integer num = mapper.countLoggerFlowQuestionReport(loggerFlowQuestionReportDTO);
		// 插入分页信息
		PageVO pageVO = PageUtil.getPageBean(loggerFlowQuestionReportDTO.getPage(), loggerFlowQuestionReportDTO.getPageCount(), num);
		result.setTotalPage(pageVO.getTotalPage());
		result.setRowNumber(pageVO.getRowNumber());
		result.setPageCount(pageVO.getPageCount());
		result.setPage(pageVO.getPage());
		return result;		
	}

	/**
	 * 导出监测水量存疑报表
	 */
	@TaskAnnotation("downloadLoggerFlowQuestionReport")
	@Override
	public XSSFWorkbook downloadLoggerFlowQuestionReport(SessionFactory factory,
			LoggerFlowQuestionReportDTO loggerFlowQuestionReportDTO, List<List<Map<String, Object>>> titleInfo) {
		OpReportMapper mapper = factory.getMapper(OpReportMapper.class);
		int days = 0;
		try {
			days = TimeUtil.dateBetween(loggerFlowQuestionReportDTO.getStartTime(), loggerFlowQuestionReportDTO.getEndTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (titleInfo == null) {
	            titleInfo = this.getLoggerFlowQuestionTitle();
	        }
	        String[][] baseInfo = new String[3][2];
	        baseInfo[0] = new String[]{"" , "Regional Scope: " + (StringUtils.isEmpty(loggerFlowQuestionReportDTO.getZoneNo()) ? "Whole Network" : loggerFlowQuestionReportDTO.getZoneNo())};
	        baseInfo[1] = new String[]{"Report ID: OP-07", "From: " + loggerFlowQuestionReportDTO.getStartTime()};
	        baseInfo[2] = new String[]{"Print Date: " + TimeUtil.getcurrentDay("yyyy-MM-dd HH:mm:ss", new Date()), "To: " + loggerFlowQuestionReportDTO.getEndTime()};
	        List<LoggerFlowQuestionReportVO> lists = mapper.queryLoggerFlowQuestionReport(loggerFlowQuestionReportDTO, days);
	        for (LoggerFlowQuestionReportVO loggerFlowQuestionReportVO : lists) {
				int doubts = loggerFlowQuestionReportVO.getMissingData() != null?loggerFlowQuestionReportVO.getMissingData():0;
				doubts = loggerFlowQuestionReportVO.getFlow() != null?(doubts>loggerFlowQuestionReportVO.getFlow()?doubts:loggerFlowQuestionReportVO.getFlow()):doubts;
				doubts = loggerFlowQuestionReportVO.getMajor() != null?(doubts>loggerFlowQuestionReportVO.getMajor()?doubts:loggerFlowQuestionReportVO.getMajor()):doubts;
				doubts = loggerFlowQuestionReportVO.getAbnoraml() != null?(doubts>loggerFlowQuestionReportVO.getAbnoraml()?doubts:loggerFlowQuestionReportVO.getAbnoraml()):doubts;
				loggerFlowQuestionReportVO.setDays(days);
				loggerFlowQuestionReportVO.setDoubts(doubts);
				DecimalFormat df = new DecimalFormat("#.0000");
				loggerFlowQuestionReportVO.setRate(days == 0?0.0:Double.parseDouble(df.format((doubts*1.0)/(days*1.0))));
			}
	        // 饼图数据
	        String[][] pieDataInfo = new String[5][2];
	        pieDataInfo[0] = new String[]{"Operable%", "Number"};
	        int a = 0, b = 0, c = 0, d = 0;
	        for (LoggerFlowQuestionReportVO item : lists) {
	            Double p = item.getRate() == null ? null : item.getRate();
	            if (p == null) continue;
	            if (0 <= p && p <= 20) a++;
	            else if (20 < p && p <= 50) b++;
	            else if (50 < p && p <= 80) c++;
	            else if (80 < p && p <= 100) d++;
	        }
	        pieDataInfo[1] = new String[]{"[0%, 20%]", String.valueOf(a)};
	        pieDataInfo[2] = new String[]{"(20%, 50%]", String.valueOf(b)};
	        pieDataInfo[3] = new String[]{"(50%, 80%]", String.valueOf(c)};
	        pieDataInfo[4] = new String[]{"(80%, 100%]", String.valueOf(d)};
	        XSSFWorkbook wb = ExportReportUtil.export("PF Data of Doubt Report", baseInfo,
	                titleInfo, lists, ExportReportUtil.PIE_CHART, pieDataInfo, false, null, null);
	        return wb;
	}

	 /**
     * 获取监测水量存疑报表表头
     *
     * @return
     */
    private List<List<Map<String, Object>>> getLoggerFlowQuestionTitle() {
        List<List<Map<String, Object>>> title = new ArrayList<>();
        List<Map<String, Object>> list1 = new ArrayList<>();
        List<Map<String, Object>> list2 = new ArrayList<>();
        list1.add(getTitle("一级分区", "zoneNo", "1", "2"));
        list1.add(getTitle("流量计参考号", "loggerNo", "1", "2"));
        list1.add(getTitle("参考位置", "address", "1", "2"));
        list1.add(getTitle("存疑天数", "", "5", "1"));
        list1.add(getTitle("总存疑天数", "doubts", "1", "2"));
        list1.add(getTitle("总天数", "days", "1", "2"));
        list1.add(getTitle("存疑（%）", "rate", "1", "2"));
        list2.add(getTitle("缺失数据", "missingData", "1", "1"));
        list2.add(getTitle("负水量", "flow", "1", "1"));
        list2.add(getTitle("重大异常(≥|150%|)", "major", "1", "1"));
        list2.add(getTitle("轻微异常[|50%|, |150%|)", "abnoraml", "1", "1"));
        title.add(list1);
        title.add(list2);
        return title;
    }
    
    private static Map<String, Object> getTitle(String titleName, String titleValue, String colspan, String rowspan) {
        Map<String, Object> map = new HashMap<>();
        map.put("titleName", titleName);
        map.put("titleValue", titleValue);
        map.put("colspan", colspan);
        map.put("rowspan", rowspan);
        return map;
    }
}

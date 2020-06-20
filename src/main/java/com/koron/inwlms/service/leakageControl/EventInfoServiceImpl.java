package com.koron.inwlms.service.leakageControl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.common.UploadFileDTO;
import com.koron.inwlms.bean.DTO.leakageControl.EventInfoDTO;
import com.koron.inwlms.bean.DTO.leakageControl.EventSubTypeDTO;
import com.koron.inwlms.bean.DTO.leakageControl.EventTypeDTO;
import com.koron.inwlms.bean.DTO.leakageControl.PageInfo;
import com.koron.inwlms.bean.DTO.leakageControl.QueryEventFileDTO;
import com.koron.inwlms.bean.DTO.sysManager.DataDicDTO;
import com.koron.inwlms.bean.VO.leakageControl.DataDicRelationVO;
import com.koron.inwlms.bean.VO.leakageControl.EventInfo;
import com.koron.inwlms.bean.VO.leakageControl.EventInfoListReturnVO;
import com.koron.inwlms.bean.VO.leakageControl.EventWarnRelation;
import com.koron.inwlms.mapper.common.FileMapper;
import com.koron.inwlms.mapper.leakageControl.AlarmProcessMapper;
import com.koron.inwlms.mapper.leakageControl.EventInfoMapper;
import com.koron.inwlms.mapper.sysManager.UserMapper;
import com.koron.util.Constant;


@Service
public class EventInfoServiceImpl implements EventInfoService{
	
	@TaskAnnotation("queryEventInfo")
	@Override
	public EventInfoListReturnVO queryEventInfo(SessionFactory factory,EventInfoDTO eventInfoDTO){
		EventInfoListReturnVO eventInfoListReturnVO = new EventInfoListReturnVO();
		EventInfoMapper mapper = factory.getMapper(EventInfoMapper.class);
		List<EventInfo> list = mapper.queryEventInfo(eventInfoDTO);
		eventInfoListReturnVO.setEventInfoList(list);
		Integer num = mapper.queryEventInfoTotalNumber(eventInfoDTO);
		PageInfo query = new PageInfo();
		query.setTotalNumber(num);
		query.setPage(eventInfoDTO.getPage());
		query.setSize(eventInfoDTO.getPageCount());
		eventInfoListReturnVO.setQuery(query);
		return eventInfoListReturnVO;
	}
	
	@TaskAnnotation("queryEventInfoByCode")
	@Override
	public EventInfo queryEventInfoByCode(SessionFactory factory,String code){
		EventInfoMapper mapper = factory.getMapper(EventInfoMapper.class);
		EventInfo list = mapper.queryEventInfoByCode(code);
		return list;
	}
	
	@TaskAnnotation("deleteEventInfo")
	@Override
	public Integer deleteEventInfo(SessionFactory factory, String code) {
		EventInfoMapper mapper = factory.getMapper(EventInfoMapper.class);
		Integer num = mapper.deleteEventInfo(code);
		return num;
	}
	
	@TaskAnnotation("updateEventInfo")
	@Override
	public Integer updateEventInfo(SessionFactory factory, EventInfo eventInfo) {
		EventInfoMapper mapper = factory.getMapper(EventInfoMapper.class);
		Integer num = mapper.updateEventInfo(eventInfo);
		return num;
	}
	
	@TaskAnnotation("addEventInfo")
	@Override
	public String addEventInfo(SessionFactory factory, EventInfo eventInfo) {
		EventInfoMapper mapper = factory.getMapper(EventInfoMapper.class);
		String code = mapper.addEventInfo(eventInfo);
		return code;
		
	}
	
	@TaskAnnotation("querychildKey")
	@Override
	public List<DataDicRelationVO> querychildKey(SessionFactory factory,EventTypeDTO eventTypeDTO){
		EventInfoMapper mapper = factory.getMapper(EventInfoMapper.class);
		List<DataDicRelationVO> list = mapper.querychildKey(eventTypeDTO);
		if(list != null && list.size() != 0) {
			Integer num = mapper.queryChildKeyNum(eventTypeDTO);
			list.get(0).setTotalNum(num);
		}
		return list;
	}
	
	@TaskAnnotation("queryMaxKey")
	@Override
	public Integer queryMaxKey(SessionFactory factory,String parent) {
		EventInfoMapper mapper = factory.getMapper(EventInfoMapper.class);
		Integer maxKey = mapper.queryMaxKey(parent);
		return maxKey;
	}
	
	@TaskAnnotation("addEventSubType")
	@Override
	public Integer addEventSubType(SessionFactory factory,EventSubTypeDTO eventSubTypeDTO) {
		//插入数据字典关联表
		EventInfoMapper mapper = factory.getMapper(EventInfoMapper.class);
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		Integer relationNum = 0;
		
		int i = 0;
		List<DataDicDTO> dataDicDTOList=new ArrayList<DataDicDTO>();
		for(DataDicDTO dataDicDTO : eventSubTypeDTO.getDataDicDTOList()) {
			relationNum = mapper.addEventTypeRelation(eventSubTypeDTO.getParentKey(), dataDicDTO.getDicKey());
			if(relationNum > 0) {
				DataDicDTO dataDicDTONew = new DataDicDTO();
				dataDicDTONew.setDicCn(Constant.EVENTSUBTYPE_CN);
				dataDicDTONew.setDicEn(Constant.EVENTSUBTYPE_EN);
				dataDicDTONew.setDicTc(Constant.EVENTSUBTYPE_TC);
				dataDicDTONew.setDicParent(Constant.EVENTSUBTYPE);
				dataDicDTONew.setDicRemark("");
				dataDicDTONew.setDicKey(dataDicDTO.getDicKey());
				dataDicDTONew.setDicEnValue(dataDicDTO.getDicEnValue());
				dataDicDTONew.setDicTcValue(dataDicDTO.getDicTcValue());
				dataDicDTONew.setDicValue(dataDicDTO.getDicValue());
				dataDicDTONew.setDicDetRemark(dataDicDTO.getDicDetRemark());
				dataDicDTONew.setDicSeq(i);
				//TODO 创建人等
				dataDicDTOList.add(dataDicDTONew);
			}
		}
		Integer eventSubTypeNum = userMapper.addDataDic(dataDicDTOList);
		
		return eventSubTypeNum;
	}
	
	@TaskAnnotation("deleteEventSubType")
	@Override
	public Integer deleteEventSubType(SessionFactory factory,String key) {
		EventInfoMapper mapper = factory.getMapper(EventInfoMapper.class);
		//删除关联表的相关信息
		mapper.deleteEventSubType(key);
		//删除数据字典信息
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		List<DataDicDTO> dataDicDTOList = new ArrayList<DataDicDTO>();
		DataDicDTO dataDicDTO = new DataDicDTO();
		dataDicDTO.setDicKey(key);
		dataDicDTOList.add(dataDicDTO);
		Integer delRes = userMapper.deleteDetDicByKey(dataDicDTOList);
		return delRes;
	}
	
	@TaskAnnotation("addEventWarnRelation")
	@Override
	public Integer addEventWarnRelation(SessionFactory factory,List<EventWarnRelation> eventWarnRelationList) {
		EventInfoMapper mapper = factory.getMapper(EventInfoMapper.class);
		Integer num = mapper.addEventWarnRelation(eventWarnRelationList);
		return num;
	}
	
	@TaskAnnotation("queryEventWarnRelation")
	@Override
	public List<EventWarnRelation> queryEventWarnRelation(SessionFactory factory, String processCode) {
		EventInfoMapper mapper = factory.getMapper(EventInfoMapper.class);
		List<EventWarnRelation> list = mapper.queryEventWarnRelation(processCode);
		return list;
	}
	
	@TaskAnnotation("deleteEventWarnRelation")
	@Override
	public Integer deleteEventWarnRelation(SessionFactory factory,String processCode,String eventCode) {
		EventInfoMapper mapper = factory.getMapper(EventInfoMapper.class);
		Integer num = mapper.deleteEventWarnRelation(processCode, eventCode);
		return num;
	}
	
	@TaskAnnotation("queryFileDataById")
	@Override
	public UploadFileDTO queryFileDataById(SessionFactory factory,Integer id) {
		EventInfoMapper mapper = factory.getMapper(EventInfoMapper.class);
		UploadFileDTO uploadFileDTO = mapper.queryFileById(id);
		return uploadFileDTO;
	}
	
	@TaskAnnotation("queryEventFile")
	@Override
	public List<UploadFileDTO> queryEventFile(SessionFactory factory,QueryEventFileDTO queryEventFileDTO){
		EventInfoMapper mapper = factory.getMapper(EventInfoMapper.class);
		List<UploadFileDTO> list = mapper.queryEventFile(queryEventFileDTO);
		return list;
	}
	
	
	@TaskAnnotation("deleteFileRelation")
	@Override
	public Integer deleteFileRelation(SessionFactory factory,QueryEventFileDTO queryEventFileDTO){
		EventInfoMapper mapper = factory.getMapper(EventInfoMapper.class);
		Integer num = mapper.deleteFileRelation(queryEventFileDTO);
		if(num != 0) {
			FileMapper filemapper = factory.getMapper(FileMapper.class);
			int result = filemapper.deleteFileById(queryEventFileDTO.getFileId());
		}
		return num;
	}
	
	
	public static List<EventInfo> readEvetInfo(File file) throws IOException, ParseException {
    	InputStream is = new FileInputStream(file);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        List<EventInfo> eventInfoList = new ArrayList<>();
        // 循环工作表Sheet
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            // 循环行Row
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow == null) {
                    continue;
                }
                EventInfo eventInfo = new EventInfo();
                // 循环列Cell
                XSSFCell xm = xssfRow.getCell(0);
                if (xm != null) {
                	eventInfo.setType(getValue(xm));
                }
                
                XSSFCell xssf = xssfRow.getCell(1);
                if (xssf != null) {
                	eventInfo.setObjectType(getValue(xssf));
                }
                

                XSSFCell code = xssfRow.getCell(2);
                if (code != null) {
                	eventInfo.setSubtype(getValue(code));
                }
                
                
                XSSFCell xssff = xssfRow.getCell(3);
                if (xssff != null) {
                	eventInfo.setFirstPartition(getValue(xssff));
                }
                
                
                XSSFCell xssfff = xssfRow.getCell(4);
                if (xssfff != null) {
                	eventInfo.setSecondPartition(getValue(xssfff));
                }
                
                
                XSSFCell xssffff = xssfRow.getCell(5);
                if (xssffff != null) {
                	eventInfo.setDmaCode(getValue(xssffff));
                }
                
                
                XSSFCell xssfffff = xssfRow.getCell(6);
                if (xssfffff != null) {
                	String time = getValue(xssfffff);
                	long timeL = Long.parseLong(time);
                    Date timeDate = new Date(timeL);
                    eventInfo.setHappenTime(timeDate);
                }
                
                
                XSSFCell xssffffff = xssfRow.getCell(7);
                if (xssffffff != null) {
                	eventInfo.setObjectCode(getValue(xssffffff));
                }
                
                XSSFCell xssfffffff = xssfRow.getCell(8);
                if (xssfffffff != null) {
                	eventInfo.setContent(getValue(xssfffffff));
                }
                eventInfoList.add(eventInfo);
                     
            }
        }
        return eventInfoList;
    }
	
	//获取表格的值
    private static String getValue(XSSFCell hssfCell) {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            // 返回布尔类型的值
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            // 返回数值类型的值
            return String.valueOf(hssfCell.getNumericCellValue());
        } else {
            // 返回字符串类型的值
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }
    
    

}

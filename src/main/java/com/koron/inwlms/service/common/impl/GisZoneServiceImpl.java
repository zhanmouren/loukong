package com.koron.inwlms.service.common.impl;

import java.util.ArrayList;
import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.github.pagehelper.util.StringUtil;
import com.koron.indicator.bean.CalZoneInfos;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryVZoneInfoDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryZoneInfoDTO;
import com.koron.inwlms.bean.VO.apparentLoss.MeterInfo;
import com.koron.inwlms.bean.VO.apparentLoss.MeterRunAnalysisVO;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneInfo;
import com.koron.inwlms.bean.VO.zoneLoss.PositionInfoVO;
import com.koron.inwlms.bean.VO.zoneLoss.VZoneInfoVO;
import com.koron.inwlms.bean.VO.zoneLoss.ZoneDetailInfoVO;
import com.koron.inwlms.mapper.common.GisMapper;
import com.koron.inwlms.service.common.GisZoneService;
import com.koron.util.Constant;

/**
 * gis接口(gis那边没开发完，用于测试)
 * @author lu
 *
 */
@Service
public class GisZoneServiceImpl implements GisZoneService {

	@TaskAnnotation("queryZoneNosByRank")
	@Override
	public List<ZoneInfo> queryZoneNosByRank(SessionFactory factory, Integer zoneRank,String zoneNo) {
		GisMapper mapper = factory.getMapper(GisMapper.class);
		if(zoneRank == null) return null;
		List<ZoneInfo> list =  new ArrayList<>();
		if(Constant.RANK_F == zoneRank) {
			list = mapper.queryZoneNosByRank(Constant.DMAZONELEVEL_ONE,zoneNo);
//			ZoneInfo zoneInfo = new ZoneInfo();
//			zoneInfo.setZoneNo("FL01");
//			zoneInfo.setZoneName("FL01一级分区");
//			zoneInfo.setAddress("FL01一级分区地址");
//			ZoneInfo zoneInfo1 = new ZoneInfo();
//			zoneInfo1.setZoneNo("FL02");
//			zoneInfo1.setZoneName("FL02一级分区");
//			zoneInfo1.setAddress("FL02一级分区地址");
//			list.add(zoneInfo);
//			list.add(zoneInfo1);
		}else if (Constant.RANK_S == zoneRank) {
			list = mapper.queryZoneNosByRank(Constant.DMAZONELEVEL_TWO,zoneNo);
//			ZoneInfo zoneInfo = new ZoneInfo();
//			zoneInfo.setZoneNo("SL01001");
//			zoneInfo.setZoneName("SL01001二级分区");
//			zoneInfo.setpZoneNo("FL01");
//			zoneInfo.setpZoneName("FL01一级分区");
//			zoneInfo.setAddress("SL01001二级分区地址");
//			ZoneInfo zoneInfo1 = new ZoneInfo();
//			zoneInfo1.setZoneNo("SL01002");
//			zoneInfo1.setZoneName("SL01002二级分区");
//			zoneInfo1.setpZoneNo("FL01");
//			zoneInfo1.setpZoneName("FL01一级分区");
//			zoneInfo1.setAddress("SL01002二级分区地址");
//			ZoneInfo zoneInfo2 = new ZoneInfo();
//			zoneInfo2.setZoneNo("SL02001");
//			zoneInfo2.setZoneName("SL02001二级分区");
//			zoneInfo2.setpZoneNo("FL02");
//			zoneInfo2.setpZoneName("FL02一级分区");
//			zoneInfo2.setAddress("SL02001二级分区地址");
//			ZoneInfo zoneInfo3 = new ZoneInfo();
//			zoneInfo3.setZoneNo("SL02002");
//			zoneInfo3.setZoneName("SL02002二级分区");
//			zoneInfo3.setpZoneNo("FL02");
//			zoneInfo3.setpZoneName("FL02一级分区");
//			zoneInfo3.setAddress("SL02002二级分区地址");
//			list.add(zoneInfo);
//			list.add(zoneInfo1);
//			list.add(zoneInfo2);
//			list.add(zoneInfo3);
		}else if (Constant.RANK_T == zoneRank) {
			list = mapper.queryZoneNosByRank(Constant.DMAZONELEVEL_THREE,zoneNo);
//			ZoneInfo zoneInfo = new ZoneInfo();
//			zoneInfo.setZoneNo("DM01001001");
//			zoneInfo.setZoneName("DM01001001DMA分区");
//			zoneInfo.setpZoneNo("SL01001");
//			zoneInfo.setpZoneName("SL01001二级分区");
//			zoneInfo.setAddress("DM01001001DMA分区地址");
//			ZoneInfo zoneInfo1 = new ZoneInfo();
//			zoneInfo1.setZoneNo("DM01001002");
//			zoneInfo1.setZoneName("DM01001002DMA分区");
//			zoneInfo1.setpZoneNo("SL01001");
//			zoneInfo1.setpZoneName("SL01001二级分区");
//			zoneInfo1.setAddress("DM01001002DMA分区地址");
//			ZoneInfo zoneInfo2 = new ZoneInfo();
//			zoneInfo2.setZoneNo("DM01002001");
//			zoneInfo2.setZoneName("DM01002001DMA分区");
//			zoneInfo2.setpZoneNo("SL01002");
//			zoneInfo2.setpZoneName("SL01002二级分区");
//			zoneInfo2.setAddress("DM01002001DMA分区地址");
//			ZoneInfo zoneInfo3 = new ZoneInfo();
//			zoneInfo3.setZoneNo("DM01002002");
//			zoneInfo3.setZoneName("DM01002002DMA分区");
//			zoneInfo3.setpZoneNo("SL01002");
//			zoneInfo3.setpZoneName("SL01002二级分区");
//			zoneInfo3.setAddress("DM01002002DMA分区地址");
//			ZoneInfo zoneInfo4 = new ZoneInfo();
//			zoneInfo4.setZoneNo("DM02001001");
//			zoneInfo4.setZoneName("DM02001001DMA分区");
//			zoneInfo4.setpZoneNo("SL02001");
//			zoneInfo4.setpZoneName("SL02001二级分区");
//			zoneInfo4.setAddress("DM02001001DMA分区地址");
//			ZoneInfo zoneInfo5 = new ZoneInfo();
//			zoneInfo5.setZoneNo("DM02001002");
//			zoneInfo5.setZoneName("DM02001002DMA分区");
//			zoneInfo5.setpZoneNo("SL02001");
//			zoneInfo5.setpZoneName("SL02001二级分区");
//			zoneInfo5.setAddress("DM02001002DMA分区地址");
//			ZoneInfo zoneInfo6 = new ZoneInfo();
//			zoneInfo6.setZoneNo("DM02002001");
//			zoneInfo6.setZoneName("DM02002001DMA分区");
//			zoneInfo6.setpZoneNo("SL02002");
//			zoneInfo6.setpZoneName("SL02002二级分区");
//			zoneInfo6.setAddress("DM02002001DMA分区地址");
//			ZoneInfo zoneInfo7 = new ZoneInfo();
//			zoneInfo7.setZoneNo("DM02002002");
//			zoneInfo7.setZoneName("DM02002002DMA分区");
//			zoneInfo7.setpZoneNo("SL02002");
//			zoneInfo7.setpZoneName("SL02002二级分区");
//			zoneInfo7.setAddress("DM02002002DMA分区地址");
//			list.add(zoneInfo);
//			list.add(zoneInfo1);
//			list.add(zoneInfo2);
//			list.add(zoneInfo3);
//			list.add(zoneInfo4);
//			list.add(zoneInfo5);
//			list.add(zoneInfo6);
//			list.add(zoneInfo7);
		}else {
			return null;
		}
		return list;
	}

	@TaskAnnotation("querySubZoneNos")
	@Override
	public List<ZoneInfo> querySubZoneNos(SessionFactory factory, String zoneNo) {
		GisMapper mapper = factory.getMapper(GisMapper.class);
		List<ZoneInfo> list =  new ArrayList<>();
		if(StringUtil.isEmpty(zoneNo)) {
			list = mapper.queryZoneNosByRank(Constant.DMAZONELEVEL_ONE,"");
//			ZoneInfo zoneInfo = new ZoneInfo();
//			zoneInfo.setZoneNo("FL01");
//			zoneInfo.setZoneName("FL01一级分区");
//			list.add(zoneInfo);
//			ZoneInfo zoneInfo1 = new ZoneInfo();
//			zoneInfo1.setZoneNo("FL02");
//			zoneInfo1.setZoneName("FL02一级分区");
//			list.add(zoneInfo);
//			list.add(zoneInfo1);	
		}else {
			if(("FL01").equals(zoneNo)) {
				ZoneInfo zoneInfo = new ZoneInfo();
				zoneInfo.setZoneNo("SL01001");
				zoneInfo.setZoneName("SL01001二级分区");
				list.add(zoneInfo);
				ZoneInfo zoneInfo1 = new ZoneInfo();
				zoneInfo1.setZoneNo("SL01002");
				zoneInfo1.setZoneName("SL01002二级分区");
				list.add(zoneInfo);
				list.add(zoneInfo1);
			}else if(("FL02").equals(zoneNo)) {
				ZoneInfo zoneInfo = new ZoneInfo();
				zoneInfo.setZoneNo("SL02001");
				zoneInfo.setZoneName("SL02001二级分区");
				list.add(zoneInfo);
				ZoneInfo zoneInfo1 = new ZoneInfo();
				zoneInfo1.setZoneNo("SL02002");
				zoneInfo1.setZoneName("SL02002二级分区");
				list.add(zoneInfo);
				list.add(zoneInfo1);
			}else if(("SL01001").equals(zoneNo)) {
				ZoneInfo zoneInfo = new ZoneInfo();
				zoneInfo.setZoneNo("DM01001001");
				zoneInfo.setZoneName("DM01001001DMA分区");
				list.add(zoneInfo);
				ZoneInfo zoneInfo1 = new ZoneInfo();
				zoneInfo1.setZoneNo("DM01001002");
				zoneInfo1.setZoneName("DM01001002DMA分区");
				list.add(zoneInfo);
				list.add(zoneInfo1);
			}else if(("SL01002").equals(zoneNo)) {
				ZoneInfo zoneInfo = new ZoneInfo();
				zoneInfo.setZoneNo("DM01002001");
				zoneInfo.setZoneName("DM01002001DMA分区");
				list.add(zoneInfo);
				ZoneInfo zoneInfo1 = new ZoneInfo();
				zoneInfo1.setZoneNo("DM01002002");
				zoneInfo1.setZoneName("DM01002002DMA分区");
				list.add(zoneInfo);
				list.add(zoneInfo1);
			}else if(("SL02001").equals(zoneNo)) {
				ZoneInfo zoneInfo = new ZoneInfo();
				zoneInfo.setZoneNo("DM02001001");
				zoneInfo.setZoneName("DM02001001DMA分区");
				list.add(zoneInfo);
				ZoneInfo zoneInfo1 = new ZoneInfo();
				zoneInfo1.setZoneNo("DM02001002");
				zoneInfo1.setZoneName("DM02001002DMA分区");
				list.add(zoneInfo);
				list.add(zoneInfo1);
			}else if(("SL02002").equals(zoneNo)) {
				ZoneInfo zoneInfo = new ZoneInfo();
				zoneInfo.setZoneNo("DM02002001");
				zoneInfo.setZoneName("DM02002001DMA分区");
				list.add(zoneInfo);
				ZoneInfo zoneInfo1 = new ZoneInfo();
				zoneInfo1.setZoneNo("DM02002002");
				zoneInfo1.setZoneName("DM02002002DMA分区");
				list.add(zoneInfo);
				list.add(zoneInfo1);
			}
		}
		
		return list;
	}

	@TaskAnnotation("queryMeterByZoneNo")
	@Override
	public List<MeterInfo> queryMeterByZoneNo(SessionFactory factory, String zoneNo) {
		List<MeterInfo> lists = new ArrayList<>();
		MeterInfo meterInfo = new MeterInfo();
		meterInfo.setAccName("xxx公司");
		meterInfo.setUseType(Constant.USER_TYPE_PP);
		meterInfo.setMeterNo("C0210280000021");
		meterInfo.setMeterDn(100);
		meterInfo.setMeterType(Constant.FS_METER);
		meterInfo.setAddress("xxxxx");
		meterInfo.setmReadDate("每月1号");
		MeterInfo meterInfo1 = new MeterInfo();
		meterInfo1.setAccName("xxx银行");
		meterInfo1.setUseType(Constant.USER_TYPE_PP);
		meterInfo1.setMeterNo("C0210280000022");
		meterInfo1.setMeterDn(100);
		meterInfo1.setMeterType(Constant.NOR_METER);
		meterInfo1.setAddress("xxxxx");
		meterInfo1.setmReadDate("每月1号");
		MeterInfo meterInfo2 = new MeterInfo();
		meterInfo2.setAccName("张某某");
		meterInfo2.setUseType(Constant.USER_TYPE_PP);
		meterInfo2.setMeterNo("C0210280000167");
		meterInfo2.setMeterDn(40);
		meterInfo2.setMeterType(Constant.NOR_METER);
		meterInfo2.setAddress("xxxxx");
		meterInfo2.setmReadDate("每月1号");
		MeterInfo meterInfo3 = new MeterInfo();
		meterInfo3.setAccName("科荣股份有限公司");
		meterInfo3.setUseType(Constant.USER_TYPE_SP);
		meterInfo3.setMeterNo("C0210280000169");
		meterInfo3.setMeterDn(50);
		meterInfo3.setMeterType(Constant.FS_METER);
		meterInfo3.setAddress("xxxxx");
		meterInfo3.setmReadDate("每月1号");
		MeterInfo meterInfo4 = new MeterInfo();
		meterInfo4.setAccName("陈某某");
		meterInfo4.setUseType(Constant.USER_TYPE_PP);
		meterInfo4.setMeterNo("C0210280000188");
		meterInfo4.setMeterDn(50);
		meterInfo4.setMeterType(Constant.NOR_METER);
		meterInfo4.setAddress("xxxxx");
		meterInfo4.setmReadDate("每月1号");
		MeterInfo meterInfo5 = new MeterInfo();
		meterInfo5.setAccName("xx洗浴中心");
		meterInfo5.setUseType(Constant.USER_TYPE_PP);
		meterInfo5.setMeterNo("C0210280000172");
		meterInfo5.setMeterDn(20);
		meterInfo5.setAddress("xxxxx");
		meterInfo5.setmReadDate("每月1号");
		meterInfo5.setMeterType(Constant.NOR_METER);
		lists.add(meterInfo);
		lists.add(meterInfo1);
		lists.add(meterInfo2);
		lists.add(meterInfo3);
		lists.add(meterInfo4);
		lists.add(meterInfo5);
		return lists;
	}

	@TaskAnnotation("queryZoneNameByNo")
	@Override
	public String queryZoneNameByNo(SessionFactory factory, String zoneNo) {
		GisMapper mapper = factory.getMapper(GisMapper.class);
		if(StringUtil.isNotEmpty(zoneNo)) {
			String name = mapper.queryZoneNameByNo(zoneNo);
			return name;
		}
		return null;
	}

	@TaskAnnotation("queryZonePositionInfo")
	@Override
	public PositionInfoVO queryZonePositionInfo(SessionFactory factory, QueryZoneInfoDTO queryZoneInfoDTO) {
		PositionInfoVO positionInfoVO = new PositionInfoVO();
		positionInfoVO.setPosition("[[[837039.33690000, 828763.76980000], [837048.45550000, 828766.73200000], [837084.96510000, 828753.66540000], [837130.94010000, 828750.06090000], [837175.56290000, 828756.36890000], [837231.45410000, 828760.87460000], [837276.72340000, 828758.45480000], [837338.47420000, 828752.59740000], [837394.81610000, 828736.82740000], [837441.64810000, 828721.46960000], [837491.27350000, 828685.01160000], [837530.53230000, 828639.61850000], [837565.23890000, 828592.75900000], [837609.86170000, 828510.30440000], [837647.27270000, 828421.99240000], [837666.26720000, 828362.64660000], [837677.08490000, 828322.09520000], [837681.14150000, 828285.14830000], [837685.64880000, 828240.09110000], [837680.24000000, 828190.07760000], [837670.06190000, 828154.31550000], [837656.35100000, 828119.78850000], [837643.28070000, 828096.76770000], [837628.14010000, 828082.92990000], [837623.00710000, 828081.44020000], [837623.38170000, 828066.06510000], [837633.30280000, 828048.53420000], [837673.80660000, 828012.44610000], [837711.66890000, 827994.84220000], [837753.72250000, 827957.62790000], [837872.28110000, 828011.65210000], [837887.96670000, 827975.29300000], [837922.78290000, 827933.32390000], [838006.78410000, 827943.26400000], [838017.28420000, 827998.48640000], [838109.02230000, 828017.26210000], [838110.35560000, 828049.84000000], [838116.06700000, 828098.62400000], [838105.11890000, 828129.23810000], [838115.86420000, 828188.63810000], [838121.03870000, 828285.62580000], [838087.40410000, 828363.21600000], [838014.96030000, 828413.64960000], [837956.74650000, 828435.63350000], [837921.81830000, 828530.03490000], [837848.08080000, 828603.74550000], [837743.29600000, 828665.81770000], [837672.14580000, 828716.25130000], [837579.00380000, 828813.23900000], [837788.57340000, 828964.53990000], [837920.78170000, 829064.65660000], [838021.68560000, 828998.70490000], [838135.52590000, 828903.01040000], [838270.06440000, 828794.38410000], [838284.29440000, 828820.24750000], [838285.58810000, 828875.85380000], [838280.41360000, 828944.39180000], [838293.34990000, 828967.66890000], [838314.04810000, 828966.37570000], [838368.38100000, 828906.88990000], [838465.40400000, 828824.12700000], [838558.54600000, 828768.52070000], [838608.99790000, 828750.41640000], [838638.75160000, 828758.17540000], [838663.33080000, 828796.97050000], [838743.53650000, 828927.58060000], [838837.97210000, 829071.12250000], [839012.02660000, 829259.23670000], [839273.34170000, 829463.55750000], [839480.32400000, 829607.09930000], [839675.66360000, 829729.95040000], [839696.86540000, 829751.23960000], [839711.09540000, 829823.65710000], [839735.67460000, 829896.07460000], [839756.37280000, 829956.85360000], [839766.47030000, 829987.39730000], [839769.56000000, 830000.00000000], [839769.90000000, 830007.43000000], [839769.77000000, 830014.66000000], [839769.27000000, 830019.94000000], [839768.60000000, 830024.76000000], [839767.75000000, 830029.55000000], [839766.65000000, 830034.52000000], [839765.20000000, 830039.29000000], [839763.52000000, 830043.97000000], [839761.61000000, 830048.58000000], [839759.49000000, 830053.08000000], [839755.36000000, 830061.08000000], [839751.99000000, 830069.03000000], [839748.98000000, 830074.69000000], [839747.07000000, 830077.91000000], [839742.22000000, 830084.02000000], [839738.20000000, 830088.55000000], [839728.94000000, 830099.53000000], [839723.98000000, 830104.90000000], [839718.46000000, 830109.78000000], [839712.42000000, 830114.62000000], [839706.76000000, 830121.86000000], [839700.58000000, 830130.69000000], [839692.74000000, 830143.55000000], [839687.08000000, 830153.94000000], [839683.89000000, 830160.43000000], [839679.56000000, 830171.43000000], [839675.17000000, 830186.72000000], [839673.53000000, 830193.67000000], [839669.86000000, 830213.14000000], [839668.94000000, 830223.80000000], [839668.11320000, 830241.05570000], [839666.90490000, 830256.47890000], [839665.34130000, 830269.76990000], [839663.99090000, 830276.23770000], [839662.50000000, 830282.20000000], [839660.77000000, 830287.90000000], [839658.70000000, 830293.80000000], [839655.24860000, 830302.17990000], [839650.41560000, 830310.99320000], [839645.01390000, 830318.81140000], [839641.03370000, 830323.92880000], [839636.40000000, 830329.00000000], [839624.94000000, 830339.77630000], [839611.49400000, 830351.72800000], [839594.12500000, 830365.90800000], [839555.81900000, 830399.31300000], [839553.11800000, 830401.40900000], [839551.73400000, 830402.71500000], [839535.61000000, 830415.83000000], [839531.91000000, 830418.94000000], [839521.18000000, 830432.52000000], [839518.45000000, 830434.44000000], [839516.80310000, 830435.79930000], [839489.70000000, 830458.17000000], [839487.06000000, 830459.99000000], [839478.60000000, 830464.70000000], [839475.40000000, 830467.10000000], [839469.40000000, 830472.60000000], [839462.60000000, 830479.50000000], [839422.40000000, 830524.50000000], [839384.90000000, 830568.00000000], [839374.90000000, 830578.60000000], [839368.50000000, 830584.80000000], [839352.00000000, 830600.00000000], [839262.21000000, 830685.82000000], [839207.95000000, 830738.13000000], [839201.78000000, 830748.30000000], [839168.55000000, 830779.74000000], [839158.94000000, 830784.30000000], [839079.67000000, 830860.86000000], [839056.20000000, 830883.29000000], [839051.09000000, 830889.32000000], [839048.10000000, 830893.28000000], [839043.03000000, 830901.30000000], [839039.25000000, 830907.64000000], [839037.09000000, 830910.66000000], [839032.40000000, 830916.42000000], [839027.00000000, 830921.96000000], [839021.60000000, 830926.62000000], [839018.65000000, 830928.86000000], [839014.29000000, 830931.90000000], [839009.47000000, 830934.88000000], [839002.26000000, 830938.66000000], [838993.05000000, 830942.76000000], [838985.57000000, 830946.45000000], [838973.05000000, 830953.42000000], [838966.70850000, 830957.49860000], [838961.56000000, 830959.91000000], [838956.09620000, 830962.20190000], [838949.78760000, 830964.19010000], [838944.63000000, 830965.72000000], [838920.20520000, 830971.75500000], [838920.20520000, 830999.50420000], [838919.75020000, 831044.07990000], [838917.02010000, 831083.19740000], [838910.64980000, 831122.76980000], [838907.00960000, 831155.97410000], [838902.00440000, 831185.99450000], [838891.82710000, 831214.88760000], [838881.36170000, 831238.54000000], [838863.16090000, 831266.28610000], [838839.95490000, 831292.21290000], [838809.92360000, 831319.95900000], [838773.97700000, 831350.88910000], [838740.76060000, 831367.26380000], [838711.18430000, 831372.72210000], [838680.69800000, 831369.99300000], [838661.29280000, 831364.70960000], [838645.06330000, 831351.08190000], [838629.48310000, 831327.07110000], [838613.25380000, 831309.54970000], [838580.14580000, 831278.40060000], [838548.33610000, 831256.98560000], [838499.64800000, 831227.78330000], [838450.31060000, 831205.07040000], [838402.27160000, 831192.74050000], [838364.61940000, 831184.95320000], [838329.56390000, 831181.70850000], [838298.40350000, 831183.65530000], [838265.94470000, 831190.79370000], [838243.22350000, 831191.44260000], [838228.29250000, 831179.76170000], [838214.42440000, 831165.69440000], [838208.58190000, 831141.03470000], [838204.03760000, 831111.83230000], [838202.09010000, 831067.70440000], [838199.49340000, 831020.98070000], [838188.45740000, 830959.98040000], [838176.77230000, 830884.70330000], [838167.08720000, 830798.58970000], [838164.49050000, 830756.40860000], [838161.89380000, 830685.02510000], [838156.70040000, 830620.13110000], [838146.31360000, 830557.18390000], [838126.83830000, 830457.24720000], [838115.29410000, 830397.71200000], [838108.80230000, 830363.96710000], [838104.25810000, 830331.52010000], [838104.25810000, 830295.82840000], [838108.80230000, 830254.94520000], [838123.08420000, 830207.57260000], [838142.55950000, 830160.20000000], [838171.12320000, 830109.58260000], [838203.58200000, 830062.21000000], [838268.91760000, 829990.31460000], [838297.32860000, 829961.23230000], [838372.64520000, 829885.86310000], [838404.01100000, 829851.32790000], [838440.37700000, 829802.25160000], [838477.45760000, 829753.91740000], [838489.95840000, 829733.92330000], [838502.68650000, 829704.84110000], [838510.18700000, 829673.03230000], [838512.00530000, 829654.17430000], [838511.77800000, 829639.17870000], [838508.82330000, 829627.81850000], [838500.33810000, 829610.40090000], [838483.97350000, 829587.68030000], [838453.51690000, 829562.00610000], [838407.83200000, 829529.51580000], [838366.32140000, 829501.67720000], [838332.00090000, 829482.59190000], [838267.90580000, 829451.46480000], [838210.17470000, 829421.92810000], [838174.56100000, 829411.44270000], [838148.42290000, 829406.67140000], [838095.69210000, 829395.08390000], [838038.64290000, 829379.86120000], [837949.12900000, 829348.88960000], [837918.89970000, 829340.25580000], [837867.53270000, 829332.07640000], [837812.75630000, 829325.94190000], [837725.31670000, 829313.63250000], [837695.99660000, 829310.90610000], [837649.85710000, 829309.31560000], [837607.80890000, 829308.40680000], [837585.66020000, 829305.39650000], [837555.43100000, 829300.39800000], [837524.97440000, 829290.17380000], [837505.65490000, 829279.49510000], [837487.92640000, 829266.54440000], [837476.96160000, 829250.75530000], [837464.68810000, 829230.98840000], [837453.55100000, 829199.63410000], [837440.36830000, 829167.37100000], [837424.58660000, 829127.22060000], [837415.72240000, 829106.09050000], [837405.94900000, 829086.77800000], [837392.76630000, 829070.19200000], [837360.94600000, 829040.20100000], [837324.80720000, 829012.70910000], [837286.94560000, 828992.19340000], [837264.89870000, 828986.51330000], [837237.39680000, 828982.87800000], [837218.30460000, 828980.60590000], [837206.94020000, 828980.37870000], [837183.75690000, 828991.05740000], [837150.57290000, 829002.87200000], [837130.34430000, 829005.14410000], [837110.34300000, 829004.00800000], [837095.56920000, 828999.23680000], [837084.36970000, 828991.19550000], [837065.73210000, 828977.33600000], [837049.59470000, 828957.56910000], [837040.04860000, 828935.30300000], [837031.18440000, 828908.03840000], [837028.36780000, 828880.65250000], [837035.39550000, 828860.55910000], [837015.80630000, 828829.90060000], [837039.33690000, 828763.76980000]]]");
		positionInfoVO.setPositionType(2);
		return positionInfoVO;
	}
	
	@TaskAnnotation("queryZoneDetailInfo")
	@Override
	public ZoneDetailInfoVO queryZoneDetailInfo(SessionFactory factory, QueryZoneInfoDTO queryZoneInfoDTO) {
		GisMapper mapper = factory.getMapper(GisMapper.class);
		Integer zoneType = queryZoneInfoDTO.getZoneType();
		String rank = "";
		if(Constant.RANK_F == zoneType) {
			rank = Constant.DMAZONELEVEL_ONE;
		}else if(Constant.RANK_S == zoneType) {
			rank = Constant.DMAZONELEVEL_TWO;
		}else if(Constant.RANK_T == zoneType) {
			rank = Constant.DMAZONELEVEL_THREE;
		}
		ZoneDetailInfoVO zoneDetailInfoVO = mapper.queryZoneDetailInfo(rank,queryZoneInfoDTO.getZoneNo());
//		ZoneDetailInfoVO zoneDetailInfoVO = new ZoneDetailInfoVO();
//		zoneDetailInfoVO.setZoneNo("FL01");
//		zoneDetailInfoVO.setZoneName("FL01一级分区");
//		zoneDetailInfoVO.setAddress("FL01一级分区地址");
//		zoneDetailInfoVO.setArea(123454.53);
//		zoneDetailInfoVO.setPipeLength(1244.3);
//		zoneDetailInfoVO.setUserNum(1245);
//		zoneDetailInfoVO.setZoneRank(1);
//		zoneDetailInfoVO.setCreateTime("2020-03-10 10:00:00");
//		zoneDetailInfoVO.setUpdateTime("2020-03-10 10:00:00");
		return zoneDetailInfoVO;
	}

	@TaskAnnotation("queryFuzzyZoneInfo")
	@Override
	public List<ZoneInfo> queryFuzzyZoneInfo(SessionFactory factory, QueryZoneInfoDTO queryZoneInfoDTO) {
		List<ZoneInfo> lists = new ArrayList<>();
		ZoneInfo zoneInfo = new ZoneInfo();
		zoneInfo.setZoneNo("FL01");
		zoneInfo.setZoneName("FL01一级分区");
		ZoneInfo zoneInfo1 = new ZoneInfo();
		zoneInfo1.setZoneNo("FL02");
		zoneInfo1.setZoneName("FL02一级分区");
		lists.add(zoneInfo);
		lists.add(zoneInfo1);	
		return lists;
	}

	@TaskAnnotation("queryVZoneInfo")
	@Override
	public List<VZoneInfoVO> queryVZoneInfo(SessionFactory factory, QueryVZoneInfoDTO queryVZoneInfoDTO) {
		List<VZoneInfoVO> lists = new ArrayList<>();
		if(queryVZoneInfoDTO.getZoneType().equals(1)) {
			//虚拟分区（合并）
			VZoneInfoVO vZoneInfoVO = new VZoneInfoVO();
			vZoneInfoVO.setZoneNo("VCZ001");
			vZoneInfoVO.setZoneName("合并虚拟分区001");
			vZoneInfoVO.setAddress("地址001");
			vZoneInfoVO.setcZoneNo("AT001,AT002");
			vZoneInfoVO.setpZoneNo("Z003");
			vZoneInfoVO.setpZoneName("实际分区003");
			VZoneInfoVO vZoneInfoVO1 = new VZoneInfoVO();
			vZoneInfoVO1.setZoneNo("VCZ002");
			vZoneInfoVO1.setZoneName("合并虚拟分区002");
			vZoneInfoVO1.setAddress("地址002");
			vZoneInfoVO1.setcZoneNo("AT003,AT004");
			vZoneInfoVO1.setpZoneNo("Z004");
			vZoneInfoVO1.setpZoneName("实际分区004");
			lists.add(vZoneInfoVO);
			lists.add(vZoneInfoVO1);
		}else if(queryVZoneInfoDTO.getZoneType().equals(2)){
			//虚拟分区（相减）
			VZoneInfoVO vZoneInfoVO = new VZoneInfoVO();
			vZoneInfoVO.setZoneNo("VSZ001");
			vZoneInfoVO.setZoneName("相减虚拟分区001");
			vZoneInfoVO.setAddress("地址001");
			vZoneInfoVO.setcZoneNo("AT001,AT002");
			vZoneInfoVO.setpZoneNo("Z001");
			vZoneInfoVO.setpZoneName("实际分区001");
			VZoneInfoVO vZoneInfoVO1 = new VZoneInfoVO();
			vZoneInfoVO1.setZoneNo("VSZ002");
			vZoneInfoVO1.setZoneName("相减虚拟分区002");
			vZoneInfoVO1.setAddress("地址002");
			vZoneInfoVO1.setcZoneNo("AT003,AT004");
			vZoneInfoVO1.setpZoneNo("Z002");
			vZoneInfoVO1.setpZoneName("实际分区002");
			lists.add(vZoneInfoVO);
			lists.add(vZoneInfoVO1);
		}
		return lists;
	}
	
	/**
	 * 根据分区编号获取分区等级（暂定）
	 * @param zoneNo
	 * @return
	 */
	@TaskAnnotation("getZoneRankByNo")
	@Override
	public Integer getZoneRankByNo(SessionFactory factory, String zoneNo) {
		if(zoneNo.contains("FL")) {
			return 1;
		}else if(zoneNo.contains("SL")) {
			return 2;
		}else if(zoneNo.contains("DM")) {
			return 3;
		}else {
			return 0;
		}
	}
	
	/**
	 * 获取分区信息集合，造数据，暂用
	 */
	public CalZoneInfos getZoneInfos() {
		CalZoneInfos calZoneInfos = new CalZoneInfos();
		List<String> firstZoneLists = new ArrayList<>();
		List<String> secondZoneLists = new ArrayList<>();
		List<String> dmaZoneLists = new ArrayList<>();
		List<String> vZoneLists = new ArrayList<>();
		firstZoneLists.add("FL01");
		firstZoneLists.add("FL02");
		secondZoneLists.add("SL01001");
		secondZoneLists.add("SL01002");
		secondZoneLists.add("SL02001");
		secondZoneLists.add("SL02002");
		dmaZoneLists.add("DM01001001");
		dmaZoneLists.add("DM01001002");
		dmaZoneLists.add("DM01002001");
		dmaZoneLists.add("DM01002002");
		dmaZoneLists.add("DM02001001");
		dmaZoneLists.add("DM02001002");
		dmaZoneLists.add("DM02002001");
		dmaZoneLists.add("DM02002002");
		vZoneLists.add("VCZ001");
		vZoneLists.add("VCZ002");
		vZoneLists.add("VSZ001");
		vZoneLists.add("VSZ002");
		calZoneInfos.setFirstZoneLists(firstZoneLists);
		calZoneInfos.setSecondZoneLists(secondZoneLists);
		calZoneInfos.setDmaZoneLists(dmaZoneLists);
		calZoneInfos.setvZoneLists(vZoneLists);
		return calZoneInfos;
	}
}

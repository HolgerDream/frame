/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.<br/>
 * 版权所有(C)2015-2020 <br/>
 * 公司名称：上海昊沧系统控制技术有限责任公司<br/>
 * 公司地址：中国上海市徐汇区云锦路500号绿地汇中心A座20楼2001<br/>
 * 网址：http://www.haocang.com/<br/>
 * <p>标        题：</p>
 * <p>文  件  名：com.gener.core.util.Chart.java</p>
 * <p>部        门：产品研发部
 * <p>版        本： 1.0</p>
 * <p>创  建  者：胡健</p>
 * <p>创建时间：2015年12月24日上午9:02:28</p>
 * <p>修  改  者：</p>
 * <p>修改时间：</p>
 */
package com.gener.core.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.gener.core.util.data.Data;

public class Chart extends AbstractUtil {

    /**
     * 描述：旋转门压缩
     * 
     * @param list
     *            待压缩的list
     * @param compressGap斜率忽略比例
     * @return 压缩后的list
     */
    public static List<? extends Data> compress(List<? extends Data> list,
	    double compressGap) {
	// 设置斜率边界
	double upGate = Double.NEGATIVE_INFINITY;
	double downGate = Double.POSITIVE_INFINITY;
	// 数据的上斜率和下斜率
	double nowUp = 0;
	double nowDown = 0;
	// 当前的对象
	Data data = null;
	Data previousData = null;
	Data lastSaveData = null;
	List<Data> reslData = new ArrayList<Data>();
	int count = 0;
	if (list.size() < 100 || compressGap == 0) {
	    return list;
	}
	lastSaveData = list.get(0);
	previousData = lastSaveData;
	reslData.add(lastSaveData);
	@SuppressWarnings("rawtypes")
	Iterator it = list.iterator();
	long interval;
	double D_value;
	while (it.hasNext()) {
	    data = (Data) it.next();
	    if (count > 0 && count < list.size() - 1) {
		interval = data.getDataDT().getTime()
			- lastSaveData.getDataDT().getTime();
		if (interval == 0) {
		    continue;
		}
		D_value = Double.valueOf(data.getValue().toString())
			- Double.valueOf(lastSaveData.getValue().toString());
		nowUp = (D_value - compressGap) / interval;
		if (nowUp > upGate) {
		    upGate = nowUp;
		}
		nowDown = (D_value + compressGap) / interval;
		if (nowDown < downGate) {
		    downGate = nowDown;
		}
		if (upGate >= downGate) {
		    reslData.add(previousData);
		    lastSaveData = previousData;
		    upGate = D_value - compressGap;
		    downGate = D_value + compressGap;
		}
	    }
	    previousData = data;
	    count++;
	}
	reslData.add(list.get(list.size() - 1));
	return reslData;
    }

    /**
     * 描述：返回该数据量大小情况下的斜率忽略比例
     * 
     * @param size
     *            数据量
     * @return 斜率忽略比例
     */
    public static double compressGap(int size) {
	double gap = 0;
	if (size <= 1000) {
	    gap = 0;
	} else if (size > 1000 && size <= 10000) {
	    gap = 0.01;
	} else if (size > 10000 && size <= 100000) {
	    gap = 0.05;
	} else {
	    gap = 0.1;
	}
	return gap;

    }

    /**
     * 描述：自动识别list大小进行旋转门压缩
     * 
     * @param list
     *            需压缩的list
     * @return 压缩后的list
     */
    public static List<? extends Data> compress(List<? extends Data> list) {
	return compress(list, compressGap(list.size()));
    }

    @SuppressWarnings("unchecked")
    public static List<? extends Data> compressBySection(
	    List<? extends Data> delist, int capacity) {
	List<Data> list = (List<Data>) delist;
	List<Data> sampList = new ArrayList<Data>();
	if (capacity >= list.size()) {
	    return list;
	} else {
	    int numOneSection = 12;
	    /* 先根据数据大小和最大显示点数分段，目前每段只取两个值，一个极大值，一个极小值 */
	    int sectionNum = capacity / numOneSection;
	    /* 每段的大小 */
	    int sectionSize = delist.size() / sectionNum;

	    double maxValue = 0;
	    double minValue = Double.MAX_VALUE;
	    int minPosition = 0;
	    int maxPosition = 0;
	    int sectionIndex = 1;
	    int interVal = sectionSize / (numOneSection - 2);
	    for (int i = 0; i < list.size(); i++) {
		if (i > sectionIndex * sectionSize) {

		    for (int j = (sectionIndex - 1) * sectionSize; j < i; j = j
			    + interVal) {
			if (minPosition >= j && maxPosition >= j) {
			    if (minPosition < maxPosition) {
				sampList.add(list.get(minPosition));
				//sampList.add(list.get(maxPosition));
			    } else {
				sampList.add(list.get(maxPosition));
				//sampList.add(list.get(minPosition));
			    }

			} else if (maxPosition >= j) {
			    sampList.add(list.get(maxPosition));
			} else if (minPosition >= j) {
			    sampList.add(list.get(minPosition));
			} else if (j != minPosition && j != maxPosition) {
			    sampList.add(list.get(j));
			}
		    }
		    maxValue = 0;
		    minValue = Double.MAX_VALUE;
		    sectionIndex++;
		}
		if (list.get(i).getValue().doubleValue() > maxValue) {
		    maxValue = list.get(i).getValue().doubleValue();
		    maxPosition = i;
		}
		if (list.get(i).getValue().doubleValue() < minValue) {
		    minValue = list.get(i).getValue().doubleValue();
		    minPosition = i;
		}
	    }
	}
	return sampList;
    }

    /**
     * 描述：直接使用滤波压缩
     * 
     * @param delist
     *            以value排序的list
     * @param capacity
     *            期望返回的数据大小
     * @return 期望大小的list
     */
    public static List<? extends Data> DataSampling(
	    List<? extends Data> delist, int capacity) {
	@SuppressWarnings("unchecked")
	List<Data> list = (List<Data>) delist;
	Data firstData = list.get(0);
	Data lastData = list.get(list.size() - 1);
	if (capacity >= list.size()) {
	    return list;
	} else {
	    SortUtil.quicksortByNum(list, 0, list.size() - 1);
	    int size = capacity / 3;
	    List<Data> sampList = new ArrayList<Data>();
	    Iterator<? extends Data> it = list.iterator();
	    int count = 0;
	    while (it.hasNext()) {
		Data data = (Data) it.next();
		if (count < size) {
		    sampList.add(data);
		}
		if (count > (list.size() / 2 - size / 2)
			&& count < (list.size() / 2 + size / 2)) {
		    sampList.add(data);
		}
		if (list.size() - count < size) {
		    sampList.add(data);
		}
		count++;
	    }
	    try {
		SortUtil.quicksortByDT(sampList, 0, sampList.size() - 1);
		if (sampList.get(0).getDataDT().getTime() != firstData
			.getDataDT().getTime()) {
		    sampList.add(0, firstData);
		}
		if (sampList.get(sampList.size() - 1).getDataDT().getTime() != lastData
			.getDataDT().getTime()) {
		    sampList.add(lastData);
		}
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    return sampList;
	}
    }
    
    public static List<? extends Data> compressData(List<? extends Data> list,
	    int capacity) {
	return compress(list);
	/*不进行压缩
	if (list.size() > capacity) {
	    return compressBySection(list, capacity);
	}*/
	//return list;
    }

    /**
     * 描述：首先调用旋转门压缩，如果数据量还是不满足期望大小，则再次压缩
     * 
     * @param list
     *            以value排序的list
     * @param capacity
     *            期望返回的数据大小
     * @return 期望大小的list
     */
    public static List<? extends Data> compressData(List<? extends Data> list,
	    int capacity,String isCompress,String compressCount) {
	boolean compress = "1".equals(isCompress);
	int count = Integer.parseInt(compressCount);
	if (!compress || list.size() < count) {
	    return list;
	} else {
	    List<? extends Data> compressList = compress(list);
	    if (compressList.size() > count) {
		return compressBySection(compressList, count);
	    } else {
		return compressList;
	    }
	}
	//return compress(list);
	/*不进行压缩
	if (list.size() > capacity) {
	    return compressBySection(list, capacity);
	}*/
	//return list;
    }

    /**
     * 描述：跳点取值
     * 
     * @param list
     *            需排序的list
     * @param capacity
     *            期望大小的容量
     * @return 期望大小的list
     */
    public static List<? extends Data> jumpData(List<? extends Data> list,
	    int capacity) {
	@SuppressWarnings("unchecked")
	List<Data> sortList = (List<Data>) list;
	List<Data> reList = new ArrayList<Data>();
	reList.add(sortList.get(0));
	int size = list.size() / capacity;
	int count = 1;
	Iterator<Data> it = sortList.iterator();
	while (it.hasNext()) {
	    Data data = (Data) it.next();
	    if (count % size == 0) {
		reList.add(data);
	    }
	    count++;
	}
	if (reList.get(reList.size() - 1).getDataDT().getTime() != sortList
		.get(sortList.size() - 1).getDataDT().getTime()) {
	    reList.add(sortList.get(sortList.size() - 1));
	}
	return reList;
    }
}

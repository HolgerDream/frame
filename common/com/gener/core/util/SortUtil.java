/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.<br/>
 * 版权所有(C)2015-2020 <br/>
 * 公司名称：上海昊沧系统控制技术有限责任公司<br/>
 * 公司地址：中国上海市徐汇区云锦路500号绿地汇中心A座20楼2001<br/>
 * 网址：http://www.haocang.com/<br/>
 * <p>标        题：</p>
 * <p>文  件  名：com.gener.core.util.SortUtil.java</p>
 * <p>部        门：产品研发部
 * <p>版        本： 1.0</p>
 * <p>创  建  者：Administrator</p>
 * <p>创建时间：2016年6月28日下午6:32:24</p>
 * <p>修  改  者：Administrator</p>
 * <p>修改时间：</p>
 */
package com.gener.core.util;

import java.util.List;
import com.gener.core.util.data.Data;

public class SortUtil extends AbstractUtil {

	/**
	 * 描述：按时间快速排序
	 * 
	 * @param list
	 *            需排序的list
	 * @param left
	 *            排序起点
	 * @param right
	 *            排序的终点
	 * @throws 排序后的list
	 */
	public static void quicksortByDT(List<Data> list, int left, int right)
			throws Exception {
		int dp;
		if (left < right) {
			dp = partitionDT(list, left, right);
			quicksortByDT(list, left, dp - 1);
			quicksortByDT(list, dp + 1, right);
		}
	}

	private static int partitionDT(List<Data> list, int left, int right)
			throws Exception {
		Data pivot = list.get(left);
		while (left < right) {
			while (left < right
					&& list.get(right).getDataDT().getTime() >= pivot
							.getDataDT().getTime())
				right--;
			if (left < right) {
				list.set(left, list.get(right));

			}
			while (left < right
					&& list.get(left).getDataDT().getTime() <= pivot
							.getDataDT().getTime())
				left++;
			if (left < right) {
				list.set(right--, list.get(left));
			}
		}
		list.set(left, pivot);
		return left;
	}

	/**
	 * 描述：根据数据大小快速排序
	 * 
	 * @param list
	 *            待排序list
	 * @param left
	 *            排序起始下标
	 * @param right
	 *            排序终止下标
	 * @throws 排序后的list
	 */
	public static void quicksortByNum(List<Data> list, int left, int right) {
		int dp;
		if (left < right) {
			dp = partitionNum(list, left, right);
			quicksortByNum(list, left, dp - 1);
			quicksortByNum(list, dp + 1, right);
		}
	}

	private static int partitionNum(List<Data> list, int left, int right) {
		Data pivot = list.get(left);
		while (left < right) {
			while (left < right
					&& list.get(right).getValue().doubleValue() >= pivot
							.getValue().doubleValue())
				right--;
			if (left < right) {
				list.set(left, list.get(right));

			}
			while (left < right
					&& list.get(left).getValue().doubleValue() <= pivot
							.getValue().doubleValue())
				left++;
			if (left < right) {
				list.set(right--, list.get(left));
			}
		}
		list.set(left, pivot);
		return left;
	}
}

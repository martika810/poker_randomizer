package com.martocio.poker_randomizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Util {
	
	public static int getMax(List<Integer> nums){
		List<Integer> clonedList=clone(nums);
		if (!nums.isEmpty()){
			Collections.sort(clonedList);
			return clonedList.get(clonedList.size()-1);
		}
			return 0;
	}
	public static List<Integer> clone(List<Integer> nums){
		List<Integer> clonedList=new ArrayList<Integer>();
		for(int num:nums){
			clonedList.add(num);
		}
		return clonedList;
	}
	
	public static List<Integer> buildIntegerArrays(int...nums){
		List<Integer> list=new ArrayList<Integer>();
		
		for(int num:nums){
			list.add(num);
		} 
		
		return list;
	}
	
	public static List<ResultHand> buildIntegerArrays(ResultHand...nums){
		List<ResultHand> list=new ArrayList<ResultHand>();
		
		for(ResultHand num:nums){
			list.add(num);
		} 
		
		return list;
	}
	
	//Pick from a vector a subset 
	public static List<Integer> getSubVectorFromPositions(List<Integer> positions,List<Integer> originalVector){
		List<Integer> subvector=new ArrayList<Integer>();
		for(int pos:positions){
			subvector.add(originalVector.get(pos));
			
		}
		return subvector;
		
	}
	
	//Pick from a vector a subset 
	public static List<ResultHand> getSubVectorResultFromPositions(List<Integer> positions,List<ResultHand> originalVector){
		List<ResultHand> subvector=new ArrayList<ResultHand>();
		for(int pos:positions){
			subvector.add(originalVector.get(pos));
				
		}
		return subvector;
	}

}

package com.itu.dataserverlogic;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

public abstract class ICommonLogic<T, V> {
//	public boolean executeActionBool(String... strs){
//		return false;
//	}
//	
//	public boolean executeActionBool(){
//		return false;
//	}
//	
//	public void executeActionNo(String... strs){
//	}
	
	public abstract V executeAction(T t);
	
	public abstract V executeAction();
}

package org.kobic.kobis.main.vo;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.kobic.kobis.file.excel.obj.XDnaRnaProteinDerivativesSheetObj;

public class D1DnaRnaProteinDerivativesVO extends XDnaRnaProteinDerivativesSheetObj{
	public D1DnaRnaProteinDerivativesVO(XDnaRnaProteinDerivativesSheetObj dnaRnaProteinDerivativesSheetRecordObj) throws IllegalAccessException, InvocationTargetException {
		BeanUtils.copyProperties( this, dnaRnaProteinDerivativesSheetRecordObj );
	}
	
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}

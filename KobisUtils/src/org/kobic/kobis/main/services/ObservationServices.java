package org.kobic.kobis.main.services;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.kobic.kobis.file.excel.obj.XObservationSheetObj;
import org.kobic.kobis.main.dao.KobisDAO;

public class ObservationServices extends AbstractKobisServices{

	public ObservationServices(String insCd, XSSFSheet sheet, KobisDAO dao) {
		super(insCd, sheet, dao);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void readRecordsInSheet() throws NoSuchMethodException, SecurityException, Exception {
		// TODO Auto-generated method stub
		if( this.getSheet().getLastRowNum() > 3 ) {
			for( int j=3; j<=this.getSheet().getLastRowNum(); j++ ) {
				XSSFRow dataRow = this.getSheet().getRow(j);

				XObservationSheetObj observationSheetRecordObj = XObservationSheetObj.getNewInstance( dataRow );
				
				System.out.println( observationSheetRecordObj.getAccess_num() );
			}
		}
	}
}
package org.kobic.kobis.rule;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import org.kobic.kobis.file.excel.obj.internal.AbstractSheetObj;
import org.kobic.kobis.mybatis.factory.MyBatisConnectionFactory;
import org.kobic.kobis.rule.dao.RuleDAO;
import org.kobic.kobis.rule.dao.RuleDAOService;
import org.kobic.kobis.rule.interpreter.LexicalInterpreter;
import org.kobic.kobis.rule.vo.RuleParamObj;
import org.kobic.kobis.rule.vo.RuleQueryVO;

public class Rule {
	private String insCd;
	private RuleDAO dao;

	public Rule( String insCd ) {
		this.insCd = insCd;

		this.dao = new RuleDAOService( MyBatisConnectionFactory.getSqlSessionFactory() );
	}

	public RuleDAO getDAO() {
		return this.dao;
	}

	private RuleParamObj makeParams( AbstractSheetObj obj ) {
		RuleParamObj params = null;

		BeanInfo info;
		try {
			params = new RuleParamObj();

			info = Introspector.getBeanInfo(obj.getClass(), Object.class);
		    PropertyDescriptor[] props = info.getPropertyDescriptors();
		    for (PropertyDescriptor pd : props) {
		        String name = pd.getName();

//				if( XCommonSheetObj.class.getField(name) != null ) {
			        Method getter = pd.getReadMethod();
					try {
						Object value = getter.invoke( obj );
				        params.addParam( name, value );
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// TODO Auto-generated catch block
//						e.printStackTrace();
					}
//				}
		    }
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			params = null;
			e.printStackTrace();
		}

		return params;
	}

	private void update( AbstractSheetObj obj, RuleParamObj params ) throws Exception {
		BeanInfo info;
		try {
			info = Introspector.getBeanInfo(obj.getClass(), Object.class);
		    PropertyDescriptor[] props = info.getPropertyDescriptors();
		    for (PropertyDescriptor pd : props) {
		        String name = pd.getName();
		        if( params.containsParam(name) ) {
			        Method setter = pd.getWriteMethod();
			        Object val = params.getParam(name);

			        if( setter != null )	setter.invoke( obj, new Object[]{ val } );
		        }
		    }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}

		return;
	}

	public boolean rule( AbstractSheetObj obj ) throws NoSuchMethodException, SecurityException, Exception {
		List<RuleQueryVO> ruleList = this.dao.getRulesByInsId( this.insCd, obj.getClass().getSimpleName() );

		if( ruleList.size() > 0 ) {
			RuleParamObj ruleObj = this.makeParams( obj );
	
			for( Iterator<RuleQueryVO> iter = ruleList.iterator(); iter.hasNext(); ) {
				LexicalInterpreter.getInstance().interpret( iter.next(), ruleObj );
			}
			this.update( obj, ruleObj );
		}

		return true;
	}
	
	public boolean validate(String statement) {
		return true;
	}
}
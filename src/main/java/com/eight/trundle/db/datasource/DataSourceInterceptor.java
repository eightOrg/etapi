package com.eight.trundle.db.datasource;

import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

@Component
public class DataSourceInterceptor {

	public DataSourceInterceptor() {
		// TODO Auto-generated constructor stub
	}
	
	public void setdataSourceDefault(JoinPoint jp) {  
		DataSourceContextHolder.setDataSourceType(DataSourceConstants.DATASOURCE_DEFAULT);  
    }  
      
    public void setdataSourceTwo(JoinPoint jp) {  
    	DataSourceContextHolder.setDataSourceType(DataSourceConstants.DATASOURCE_TWO);  
    }
    
    public void setdataSourceThree(JoinPoint jp) {  
    	DataSourceContextHolder.setDataSourceType(DataSourceConstants.DATASOURCE_THREE);  
    }
    
    public void setdataSourceFour(JoinPoint jp) {  
    	DataSourceContextHolder.setDataSourceType(DataSourceConstants.DATASOURCE_FOUR);  
    }
    
    public void setdataSourceFive(JoinPoint jp) {  
    	DataSourceContextHolder.setDataSourceType(DataSourceConstants.DATASOURCE_FIVE);  
    }
    
    public void setdataSourceSix(JoinPoint jp) {  
    	DataSourceContextHolder.setDataSourceType(DataSourceConstants.DATASOURCE_SIX);  
    }
    
    public void setdataSourceSeven(JoinPoint jp) {  
    	DataSourceContextHolder.setDataSourceType(DataSourceConstants.DATASOURCE_SEVEN);  
    }
    
    public void setdataSourceEight(JoinPoint jp) {  
    	DataSourceContextHolder.setDataSourceType(DataSourceConstants.DATASOURCE_EIGHT);  
    }
    
    public void setdataSourceNine(JoinPoint jp) {  
    	DataSourceContextHolder.setDataSourceType(DataSourceConstants.DATASOURCE_NINE);  
    }
    
    public void setdataSourceTen(JoinPoint jp) {  
    	DataSourceContextHolder.setDataSourceType(DataSourceConstants.DATASOURCE_TEN);  
    }

}

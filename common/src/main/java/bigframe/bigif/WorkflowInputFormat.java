package bigframe.bigif;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import bigframe.util.Config;

/**
 * A class to record all the information related to workflow drivers.
 * @author andy
 *
 */
public class WorkflowInputFormat extends Config {
	
	private String HADOOP_HOME = "";
	private String HDFS_ROOT_DIR = "";
	
	private String HIVE_HOME = "";
	private String HIVE_JDBC_SERVER = "";
	
	private String SHARK_HOME = "";
	private String SPARK_HOME = "";
	private String SPARK_MASTER = "";
	
	private String VERTICA_HOSTNAMES = "";
	private String VERTICA_DATABASE = "";
	private Integer VERTICA_PORT = 0;
	private String VERTICA_USERNAME = "";
	private String VERTICA_PASSWORD = "";
	private String VERTICA_JDBC_SERVER = "";
	private String VERTICA_HOME = "";
	
	private String HANA_HOSTNAMES = "";
	private String HANA_DATABASE = "";
	private Integer HANA_PORT = 0;
	private String HANA_USERNAME = "";
	private String HANA_PASSWORD = "";
	private String HANA_JDBC_SERVER = "";
	private String HANA_HOME = "";

	
	
	public WorkflowInputFormat() {
	}
	
	public String getHadoopHome() {
		return HADOOP_HOME;
	}
	
	public String getHDFSRootDIR() {
		return HDFS_ROOT_DIR;
	}
	
	public String getHiveHome() {
		return HIVE_HOME;
	}
	
	public String getHiveJDBCServer() {
		return HIVE_JDBC_SERVER;
	}
	
	public String getSharkHome() {
		return SHARK_HOME;
	}
	
	public String getSparkHome() {
		return SPARK_HOME;
	}
	
	public String getSparkMaster() {
		return SPARK_MASTER;
	}
	
	
	public String getVerticaHostNames() {
		return VERTICA_HOSTNAMES;
	}
	
	public String getVerticaUserName() {
		return VERTICA_USERNAME;
	}
	
	public String getVerticaPassword() {
		return VERTICA_PASSWORD;
	}
	
	public String getVerticaDatabase() {
		return VERTICA_DATABASE;
	}
	
	public Integer getVerticaPort() {
		return VERTICA_PORT;
	}
	
	public String getVerticaJDBCServer() {
		return VERTICA_JDBC_SERVER;
	}
	
	public String getVerticaHome(){
		return VERTICA_HOME;
	}
	
	
	
	
	
	public String getHanaHostNames() {
		return HANA_HOSTNAMES;
	}
	
	public String getHanaUserName() {
		return HANA_USERNAME;
	}
	
	public String getHanaPassword() {
		return HANA_PASSWORD;
	}
	
	public String getHanaDatabase() {
		return HANA_DATABASE;
	}
	
	public Integer getHanaPort() {
		return HANA_PORT;
	}
	
	public String getHanaJDBCServer() {
		return HANA_JDBC_SERVER;
	}
	
	public String getHanaHome(){
		return HANA_HOME;
	}

	
	
	
	public void reload() {

	}

	@Override
	protected void reloadConf() {
		for (Map.Entry<String, String> entry : properties.entrySet()) {
			String key = entry.getKey().trim();
			String value = entry.getValue().trim();
			
			if (key.equals(BigConfConstants.BIGFRAME_HADOOP_HOME)) {
				HADOOP_HOME = value;
			}
			
			if (key.equals(BigConfConstants.BIGFRAME_HDFS_ROOTDIR)) {
				HDFS_ROOT_DIR = value;
			}
			
			
			else if (key.equals(BigConfConstants.BIGFRAME_HIVE_HOME)) {
				HIVE_HOME = value;
			}
			
			else if (key.equals(BigConfConstants.BIGFRAME_HIVE_JDBC_SERVER)) {
				HIVE_JDBC_SERVER = value;
			}
			
			else if (key.equals(BigConfConstants.BIGFRAME_SHARK_HOME)) {
				SHARK_HOME = value;
			}
			
			
			else if (key.equals(BigConfConstants.BIGFRAME_SPARK_HOME)) {
				SPARK_HOME = value;
			}
			
			else if (key.equals(BigConfConstants.BIGFRAME_SPARK_MASTER)) {
				SPARK_MASTER = value;
			}
			
			else if (key.equals(BigConfConstants.BIGFRAME_VERTICA_DATABASE)) {
				VERTICA_DATABASE = value;
			}
			
			else if (key.equals(BigConfConstants.BIGFRAME_VERTICA_HOSTNAMES)) {
				VERTICA_HOSTNAMES = value;
			}
			
			else if (key.equals(BigConfConstants.BIGFRAME_VERTICA_USERNAME)) {
				VERTICA_USERNAME = value;
			}
			
			else if (key.equals(BigConfConstants.BIGFRAME_VERTICA_PASSWORD)) {
				VERTICA_PASSWORD = value;
			}
			
			else if (key.equals(BigConfConstants.BIGFRAME_VERTICA_PORT)) {
				if(!value.equals("")) {
					VERTICA_PORT = Integer.parseInt(value);
				}
			}
			
			else if (key.equals(BigConfConstants.BIGFRAME_VERTICA_JDBC_SERVER)) {
				VERTICA_JDBC_SERVER = value;
			}
			
			else if (key.equals(BigConfConstants.BIGFRAME_VERTICA_HOME)) {
				VERTICA_HOME = value;
			}
			
			
			else if (key.equals(BigConfConstants.BIGFRAME_HANA_DATABASE)) {
				HANA_DATABASE = value;
			}
			
			else if (key.equals(BigConfConstants.BIGFRAME_HANA_HOSTNAMES)) {
				HANA_HOSTNAMES = value;
			}
			
			else if (key.equals(BigConfConstants.BIGFRAME_HANA_USERNAME)) {
				HANA_USERNAME = value;
			}
			
			else if (key.equals(BigConfConstants.BIGFRAME_HANA_PASSWORD)) {
				HANA_PASSWORD = value;
			}
			
			else if (key.equals(BigConfConstants.BIGFRAME_HANA_PORT)) {
				if(!value.equals("")) {
					HANA_PORT = Integer.parseInt(value);
				}
			}
			else if (key.equals(BigConfConstants.BIGFRAME_HANA_JDBC_SERVER)) {
				HANA_JDBC_SERVER = value;
			}
			
			else if (key.equals(BigConfConstants.BIGFRAME_HANA_HOME)) {
				HANA_HOME = value;
			}
		}
		
	}

	@Override
	public void printConf() {
		System.out.println("Workflow Driver configuration:");
		System.out.println("Hadoop Home:" + HADOOP_HOME);
		System.out.println("Hadoop Root Dir: " + HDFS_ROOT_DIR);
		System.out.println("Hive Home: " + HIVE_HOME);
		System.out.println("Hive JDBC Server: " + HIVE_JDBC_SERVER);
		System.out.println("Shark Home: " + SHARK_HOME);
		System.out.println("Spark Home: " + SPARK_HOME);
		System.out.println("Spark MASTER: " + SPARK_MASTER);
		System.out.println("Vertica Host Names: " + VERTICA_HOSTNAMES);
		System.out.println("Vertica Database: " + VERTICA_DATABASE);
		System.out.println("Vertica User Name: " + VERTICA_USERNAME);
		System.out.println("Vertica Password: " + VERTICA_PASSWORD);
		System.out.println("Vertica Port: " + VERTICA_PORT);
		System.out.println("Vertica JDBC Server: " + VERTICA_JDBC_SERVER);
		
		System.out.println("Hana Host Names: " + HANA_HOSTNAMES);
		System.out.println("Hana Database: " + HANA_DATABASE);
		System.out.println("Hana User Name: " + HANA_USERNAME);
		System.out.println("Hana Password: " + HANA_PASSWORD);
		System.out.println("Hana Port: " + HANA_PORT);
		System.out.println("Hana JDBC Server: " + HANA_JDBC_SERVER);
	}
}

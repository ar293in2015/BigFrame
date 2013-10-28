package bigframe.util.dataloader.vertica;

import java.io.IOException;
import java.text.ParseException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import bigframe.bigif.WorkflowInputFormat;
//import bigframe.util.TableNotFoundException;

import com.vertica.hadoop.VerticaOutputFormat;
import com.vertica.hadoop.VerticaRecord;

/**
 * A data loader that can load tpcds data from different sources into Vertica.
 *  
 * @author andy
 *
 */
public class VerticaTpcdsLoader extends VerticaDataLoader{
	
	public VerticaTpcdsLoader(WorkflowInputFormat workIF) {
		super(workIF);
		// TODO Auto-generated constructor stub
	}



	@Override
	public boolean load() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public static class Map extends
		Mapper<LongWritable, Text, Text, VerticaRecord> {
		
		VerticaRecord record = null;
		
		String tableName;

		@Override
		public void setup(Context context) throws IOException, InterruptedException {
			super.setup(context);
			try {
				record = new VerticaRecord(context.getConfiguration());
			} catch (Exception e) {
				throw new IOException(e);
			}
			
			tableName = context.getConfiguration().get(MAPRED_VERTICA_TABLE_NAME);
		}

		@Override
	    public void map(LongWritable key, Text value, Context context)
	        throws IOException, InterruptedException {

			if (record == null) {
				throw new IOException("No output record found");
			}
			
			String [] fields = value.toString().split("\\|", -1);
			
			/**
			 * WARNING: we minus 1 to length is because the data generated
			 * by TPCDS is ended with an extra "|" 
			 */
			for(int i = 0; i < fields.length - 1; i++ ) {
				try {
//					System.out.println("The value: " + fields[i]);
					record.setFromString(i, fields[i]);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			context.write(new Text(tableName), record);
			
	    }
	}

	
	/**
	 * Load data from HDFS to Vertica.
	 * @param srcHdfsPath 	the hdfs path of source data 
	 * @param table 	the name of table to load into
	 * @return
	 * @throws TableNotFoundException 	if the table name is not found in Vertica
	 */
	@Override
	public boolean load(Path srcHdfsPath, String table) {
		
		Configuration mapred_config = new Configuration();
		
		mapred_config.addResource(new Path(workIF.getHadoopHome()
				+ "/conf/core-site.xml"));
		mapred_config.addResource(new Path(workIF.getHadoopHome()
				+ "/conf/mapred-site.xml"));
		
		mapred_config.set(MAPRED_VERTICA_DATABASE, workIF.getVerticaDatabase());
		mapred_config.set(MAPRED_VERTICA_USERNAME, workIF.getVerticaUserName());
		mapred_config.set(MAPRED_VERTICA_PASSWORD, workIF.getVerticaPassword());
		mapred_config.set(MAPRED_VERTICA_HOSTNAMES, workIF.getVerticaHostNames());
		mapred_config.set(MAPRED_VERTICA_PORT, workIF.getVerticaPort().toString());
		
		
		try {
			
			mapred_config.set(MAPRED_VERTICA_TABLE_NAME, table);
			
			Job job = new Job(mapred_config);
		    
			FileInputFormat.setInputPaths(job, srcHdfsPath);
	
		    job.setJobName("Load data to Table " + table);
		    
		    job.setOutputKeyClass(Text.class);
		    job.setOutputValueClass(VerticaRecord.class);
		    
//		    job.setMapOutputKeyClass(Text.class);
//		    job.setMapOutputValueClass(DoubleWritable.class);
		        
//		    job.setInputFormatClass(VerticaInputFormat.class);
		    job.setOutputFormatClass(VerticaOutputFormat.class);
		    
		    job.setJarByClass(VerticaTpcdsLoader.class);
		    job.setMapperClass(Map.class);
		    //job.setReducerClass(Reduce.class);
		    
		    job.setNumReduceTasks(0);
		 
		    if(table.equals("customer"))
			    VerticaOutputFormat.setOutput(job, "customer", true, "c_customer_sk int", "c_customer_id char(16)",
			        "c_current_cdemo_sk int", "c_current_hdemo_sk int", "c_current_addr_sk int", "c_first_shipto_date_sk int", 
			        "c_first_sales_date_sk int", "c_salutation char(10)", "c_first_name char(20)", "c_last_name char(30)",
			        "c_preferred_cust_flag char(1)", "c_birth_day int", "c_birth_month int", "c_birth_year int",
			        "c_birth_country varchar(20)", "c_login char(13)", "c_email_address char(50)", "c_last_review_date_sk int");
		    
		    else if(table.equals("catalog_sales"))
		    	 VerticaOutputFormat.setOutput(job, "catalog_sales", true, "cs_sold_date_sk int", "cs_sold_time_sk int", 
		    			 "cs_ship_date_sk int", "cs_bill_customer_sk int", "cs_bill_cdemo_sk int", "cs_bill_hdemo_sk int",
		    			 "cs_bill_addr_sk, int", "cs_ship_customer_sk int", "cs_ship_cdemo_sk int", "cs_ship_hdemo_sk int",
		    			 "cs_ship_addr_sk int", "cs_call_center_sk int", "cs_catalog_page_sk int", "cs_ship_mode_sk int",
		    			 "cs_warehouse_sk int", "cs_item_sk int", "cs_promo_sk int", "cs_order_number int", "cs_quantity int",
		    			 "cs_wholesale_cost float", "cs_list_price float", "cs_sales_price float", "cs_ext_discount_amt float",
		    			 "cs_ext_sales_price float", "cs_ext_wholesale_cost float", "cs_ext_list_price float",
		    			 "cs_ext_tax float", "cs_coupon_amt float", "cs_ext_ship_cost float", "cs_net_paid float", 
		    			 "cs_net_paid_inc_tax float", "cs_net_paid_inc_ship float", "cs_net_paid_inc_ship_tax float",
		    			 "cs_net_profit float");
		    
		    else if(table.equals("store_sales"))
		    	VerticaOutputFormat.setOutput(job, "store_sales", true, "ss_sold_date_sk int","ss_sold_time_sk int ","ss_item_sk int",
		    			"ss_customer_sk int","ss_cdemo_sk int","ss_hdemo_sk int","ss_addr_sk  int","ss_store_sk int","ss_promo_sk int",
		    			"ss_ticket_number int","ss_quantity int","ss_wholesale_cost float","ss_list_price float","ss_sales_price float",
		    			"ss_ext_discount_amt float","ss_ext_sales_price float","ss_ext_wholesale_cost float","ss_ext_list_price float",
		    			"ss_ext_tax float","ss_coupon_amt float","ss_net_paid float ","ss_net_paid_inc_tax float");
		    
		    else if(table.equals("web_sales"))
		    	VerticaOutputFormat.setOutput(job, "web_sales", true, "ws_sold_date_sk  int", "ws_sold_time_sk  int", "ws_ship_date_sk int", 
		    			"ws_item_sk int", "ws_bill_customer_sk  int", "ws_bill_cdemo_sk int", "ws_bill_hdemo_sk int", "ws_bill_addr_sk int", 
		    			"ws_ship_customer_sk int", "ws_ship_cdemo_sk int", "ws_ship_hdemo_sk int", "ws_ship_addr_sk int", "ws_web_page_sk int",
		    			"ws_web_site_sk int", "ws_ship_mode_sk int", "ws_warehouse_sk int", "ws_promo_sk int", "ws_order_number int", 
		    			"ws_quantity  int", "ws_wholesale_cost float", "ws_list_price float", "ws_sales_price float", "ws_ext_discount_amt float",
		    			"ws_ext_sales_price float", "ws_ext_wholesale_cost float", "ws_ext_list_price float", "ws_ext_tax float", 
		    			"ws_coupon_amt float", "ws_ext_ship_cost float", "ws_net_paid float", "ws_net_paid_inc_tax float", "ws_net_paid_inc_ship float",
		    			"ws_net_paid_inc_ship_tax  float");
		    else if(table.equals("item"))
		    	VerticaOutputFormat.setOutput(job, "item", true, "i_item_sk int", "i_item_id char(16)", "i_rec_start_date date", "i_rec_end_date date",
		    			"i_item_desc varchar(200)", "i_current_price float", "i_wholesale_cost float", "i_brand_id int", "i_brand char(50)",
		    			"i_claws_id int", "i_class char(50)", "i_category_id int", "i_category char(50)", "i_manufact_id int", "i_manufact char(50)", 
		    			"i_size char(20)", "i_formulation char(20)", "i_color char(20)", "i_units char(10)", "i_container char(10)", "i_manager_id int");
		    
		    else if(table.equals("promotion"))
		    	VerticaOutputFormat.setOutput(job, "promotion", true, "p_promo_sk int", "p_promo_id char(16)", "p_start_date_sk int", 
		    			"p_end_date_sk int", "p_item_sk int", "p_cost float", "p_response_target int", "p_promo_name char(50)",
		    			"p_channel_dmail char(1)", "p_channel_email char(1)", "p_channel_catalog char(1)", "p_channel_tv char(1)",
		    			"p_channel_radio char(1)", "p_channel_press char(1)", "p_channel_event char(1)", "p_channel_demo char(1)", 
		    			"p_channel_details varchar(100)", "p_purpose char(15)");
		    
		    else {
		    	//throw new TableNotFoundException("Table " + table + " doesn't exist!");
		    	System.out.println("Table " + table + " doesn't exist!");
		    	return false;
		    }
		
		
			return job.waitForCompletion(true);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

}
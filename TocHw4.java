//資訊系  F74009038 顧凱云

import java.net.*;
import java.util.*;
import java.io.*;
import org.json.*;

public class TocHw4{

	    public static void main(String[] args) throws Exception {
	    	  // declare the variables 
	    	String url = args[0];
	    	String[] roadJSON;  //for JSON data
	    	Integer[] yearJSON; 
	    	Integer[] priceJSON;
	    	ArrayList<String> roadType1 = new ArrayList<String>();  // use for road has "路"
	    	ArrayList<String> roadType2 = new ArrayList<String>();  // use for road has "街"
	    	ArrayList<String> roadType3 = new ArrayList<String>();  // use for road has "巷"	    	
	    	ArrayList<String> roadType4 = new ArrayList<String>();  // use for road has "大道"	 
	    	ArrayList<Integer> yearType1 = new ArrayList<Integer>();  // use for year has "路"
	    	ArrayList<Integer> yearType2 = new ArrayList<Integer>();  // use for year has "街"
	    	ArrayList<Integer> yearType3 = new ArrayList<Integer>();  // use for year has "巷"
	    	ArrayList<Integer> yearType4 = new ArrayList<Integer>();  // use for year has "大道"
	    	ArrayList<Integer> priceType1 = new ArrayList<Integer>();  // use for price has "路"
	    	ArrayList<Integer> priceType2 = new ArrayList<Integer>();  // use for price has "街"
	    	ArrayList<Integer> priceType3 = new ArrayList<Integer>();  // use for price has "巷"
	    	ArrayList<Integer> priceType4 = new ArrayList<Integer>();  // use for price has "大道"
	    	  //variables for count same road name
	    	int[] distMon1Num; // sale for distinct month which road has "路" count 
	    	int[] distMon2Num; //  sale for distinct month which road has "街" count 
	    	int[] distMon3Num; //  sale for distinct month which road has "巷" count 
	    	int[] distMon4Num; //  sale for distinct month which road has "大道" count 
	    	int maxNum1 = 0; // max count of the road has "路" sale in max distinct month
	    	int maxNum2 = 0; // max count of the road has "街" sale in max distinct month
	    	int maxNum3 = 0; // max count of the road has "巷" sale in max distinct month
	    	int maxNum4 = 0; // max count of the road has "大道" sale in max distinct month
	    	ArrayList<Integer> indexMax1 = new ArrayList<Integer>(); // index of max count of the road has "路" 
	    	ArrayList<Integer> indexMax2 = new ArrayList<Integer>(); // index of max count of the road has "街" 
	    	ArrayList<Integer> indexMax3 = new ArrayList<Integer>(); // index of max count of the road has "巷"
	    	ArrayList<Integer> indexMax4 = new ArrayList<Integer>(); // index of max count of the road has "大道" 
	    	int maxOfAll = 0;  // find max count of all;
	    	int whichIsMax = 0; // 1 => type1 is max  2 => type2 is max 3 =>type3 is max 4 =>type4 is max
	    	ArrayList<String> diffMaxRd1 = new ArrayList<String>();  // use for road has "路"(has max distinct month)
	    	ArrayList<String> diffMaxRd2 = new ArrayList<String>();  // use for road has "街"(has max distinct month)
	    	ArrayList<String> diffMaxRd3 = new ArrayList<String>();  // use for road has "巷"(has max distinct month)
	    	ArrayList<String> diffMaxRd4 = new ArrayList<String>();  // use for road has "大道"(has max distinct month)
	    	
	    	  // handle url and get data of JSON type
	        URL getUrl = new URL(url);
	        BufferedReader in = new BufferedReader(new InputStreamReader(getUrl.openStream(),"utf-8"));
	        JSONArray jsonData = new JSONArray(new JSONTokener(in));
              // new array
	        roadJSON = new String[jsonData.length()];
	    	yearJSON = new Integer[jsonData.length()]; 
	    	priceJSON = new Integer[jsonData.length()];
	          // assign value we want to array
	        for(int i=0;i<jsonData.length();i++){
	        	roadJSON[i] = jsonData.getJSONObject(i).getString("土地區段位置或建物區門牌");
	            yearJSON[i] = jsonData.getJSONObject(i).getInt("交易年月");
	            priceJSON[i] = jsonData.getJSONObject(i).getInt("總價元");
	        }	
	          //check has "路"，"街"，"巷",and assign to different type of road
	        for(int j=0;j<jsonData.length();j++){
	        	int flag = 0;
	        	 if(roadJSON[j].contains("路")){
	        		String[] roadSplit = roadJSON[j].split("路");
	        		String roadStr = roadSplit[0]+"路";
	        		roadType1.add(roadStr);
	        		yearType1.add(yearJSON[j]);
	        		priceType1.add(priceJSON[j]);
	        		flag = 1;
	        	 }
	        	 if(roadJSON[j].contains("街")){
		        	String[] roadSplit = roadJSON[j].split("街");
		        	String roadStr = roadSplit[0]+"街";
		        	roadType2.add(roadStr);
	        		yearType2.add(yearJSON[j]);
	        		priceType2.add(priceJSON[j]);
	        		flag =1;
	        	 }
	        	 if(roadJSON[j].contains("大道")){
		        	String[] roadSplit = roadJSON[j].split("大道");
		        	String roadStr = roadSplit[0]+"大道";
		        	roadType4.add(roadStr);
	        		yearType4.add(yearJSON[j]);
	        		priceType4.add(priceJSON[j]);
	        		flag =1;
	        	 }
	        	 if(flag == 0){
	        	   if(roadJSON[j].contains("巷")){
		        	  String[] roadSplit = roadJSON[j].split("巷");
		        	  String roadStr = roadSplit[0]+"巷";
		        	  roadType3.add(roadStr);
	        		  yearType3.add(yearJSON[j]);
	        		  priceType3.add(priceJSON[j]);
	        	   }
	        	 }
	        }
	          // new array
	        distMon1Num = new int[roadType1.size()];
	          // initialize array
	        for(int i=0;i<distMon1Num.length;i++)
	        	distMon1Num[i] = 1;  // initial is 1
	        
       	    for(int i=0;i<roadType1.size();i++){
    	    	ArrayList<String> monType1 = new ArrayList<String>();
    		    for(int j=0;j<roadType1.size();j++){
    			    if(roadType1.get(i).equals(roadType1.get(j))){
                        if(!yearType1.get(i).toString().equals(yearType1.get(j).toString())){
                        	if(monType1.size()==0)
                        		monType1.add(yearType1.get(j).toString());
                        	if(monType1.size()>=1){
                        		int diff = 1;
                    		    for(int k=0;k<monType1.size();k++){
                            		if(!monType1.get(k).equals(yearType1.get(j).toString()))
                             		   diff *= 1;
                            		else
                            		   diff *= 0;
                    		    }
                    		    if(diff == 1)
                    		        monType1.add(yearType1.get(j).toString());
                        	}         		
                        }
    			    }		
    		    }
    		    distMon1Num[i] += monType1.size();
    	    } 
    
       	      // new array
	        distMon2Num = new int[roadType2.size()];
	          // initialize array
	        for(int i=0;i<distMon2Num.length;i++)
	        	distMon2Num[i] = 0;
	        
       	    for(int i=0;i<roadType2.size();i++){
    	    	ArrayList<String> monType2 = new ArrayList<String>();
    		    for(int j=0;j<roadType2.size();j++){
    			    if(roadType2.get(i).equals(roadType2.get(j))){
                        if(!yearType2.get(i).toString().equals(yearType2.get(j).toString())){
                        	if(monType2.size()==0)
                        		monType2.add(yearType2.get(j).toString());
                        	if(monType2.size()>=1){
                        		int diff = 1;
                    		    for(int k=0;k<monType2.size();k++){
                            		if(!monType2.get(k).equals(yearType2.get(j).toString()))
                             		   diff *= 1;
                            		else
                            		   diff *= 0;
                    		    }
                    		    if(diff == 1)
                    		        monType2.add(yearType2.get(j).toString());
                        	}         		
                        }
    			    }		
    		    }
    		    distMon2Num[i] += monType2.size();
    	    } 
       	      // new array
	        distMon3Num = new int[roadType3.size()];
	          // initialize array
	        for(int i=0;i<distMon3Num.length;i++)
	        	distMon3Num[i] = 0;
	        
       	    for(int i=0;i<roadType3.size();i++){
    	    	ArrayList<String> monType3 = new ArrayList<String>();
    		    for(int j=0;j<roadType3.size();j++){
    			    if(roadType3.get(i).equals(roadType3.get(j))){
                        if(!yearType3.get(i).toString().equals(yearType3.get(j).toString())){
                        	if(monType3.size()==0)
                        		monType3.add(yearType3.get(j).toString());
                        	if(monType3.size()>=1){
                        		int diff = 1;
                    		    for(int k=0;k<monType3.size();k++){
                            		if(!monType3.get(k).equals(yearType3.get(j).toString()))
                             		   diff *= 1;
                            		else
                            		   diff *= 0;
                    		    }
                    		    if(diff == 1)
                    		        monType3.add(yearType3.get(j).toString());
                        	}         		
                        }
    			    }		
    		    }
    		    distMon3Num[i] += monType3.size();
    	    } 
     	      // new array
	        distMon4Num = new int[roadType4.size()];
	          // initialize array
	        for(int i=0;i<distMon4Num.length;i++)
	        	distMon4Num[i] = 0;
	        
     	    for(int i=0;i<roadType4.size();i++){
  	    	  ArrayList<String> monType4 = new ArrayList<String>();
  		      for(int j=0;j<roadType4.size();j++){
  			     if(roadType4.get(i).equals(roadType4.get(j))){
                      if(!yearType4.get(i).toString().equals(yearType4.get(j).toString())){
                      	if(monType4.size()==0)
                      		monType4.add(yearType4.get(j).toString());
                      	if(monType4.size()>=1){
                      		int diff = 1;
                  		    for(int k=0;k<monType4.size();k++){
                          		if(!monType4.get(k).equals(yearType4.get(j).toString()))
                           		   diff *= 1;
                          		else
                          		   diff *= 0;
                  		    }
                  		    if(diff == 1)
                  		        monType4.add(yearType4.get(j).toString());
                      	}         		
                      }
  			      }		
  		       }
  		       distMon4Num[i] += monType4.size();
  	        } 
       	    
       	      //find max count
       	    for(int i=0;i<distMon1Num.length;i++){
       	    	if(distMon1Num[i]>maxNum1){
       	    		maxNum1 = distMon1Num[i];
       	    	}	
       	    }
       	      //check more than one road has max count
       	    for(int i=0;i<distMon1Num.length;i++){
       	    	if(distMon1Num[i] == maxNum1){
       	    		indexMax1.add(i);
       	    		if(diffMaxRd1.size()==0){
       	    			diffMaxRd1.add(roadType1.get(i));
       	    		}
       	    		if(diffMaxRd1.size()>=1){
       	    			int diff = 1;
            		    for(int k=0;k<diffMaxRd1.size();k++){
                    		if(!diffMaxRd1.get(k).equals(roadType1.get(i)))
                     		   diff *= 1;
                    		else
                    		   diff *= 0;
            		    }
            		    if(diff == 1)
            		        diffMaxRd1.add(roadType1.get(i));
       	    		} 
       	    	}	
       	    }
     	      //find max count
     	    for(int i=0;i<distMon2Num.length;i++){
     	    	if(distMon2Num[i]>maxNum2){
     	    		maxNum2 = distMon2Num[i];
     	    	}	
     	    }
     	      //check more than one road has max count
       	    for(int i=0;i<distMon2Num.length;i++){
       	    	if(distMon2Num[i] == maxNum2){
       	    		indexMax2.add(i);
       	    		if(diffMaxRd2.size()==0){
       	    			diffMaxRd2.add(roadType2.get(i));
       	    		}
       	    		if(diffMaxRd2.size()>=1){
       	    			int diff = 1;
            		    for(int k=0;k<diffMaxRd2.size();k++){
                    		if(!diffMaxRd2.get(k).equals(roadType2.get(i)))
                     		   diff *= 1;
                    		else
                    		   diff *= 0;
            		    }
            		    if(diff == 1)
            		        diffMaxRd2.add(roadType2.get(i));
       	    		} 
       	    	}	
       	    }
     	      //find max count
     	    for(int i=0;i<distMon3Num.length;i++){
     	    	if(distMon3Num[i]>maxNum3){
     	    		maxNum3 = distMon3Num[i];
     	    	}	
     	    }
     	      //check more than one road has max count
       	    for(int i=0;i<distMon3Num.length;i++){
       	    	if(distMon3Num[i] == maxNum3){
       	    		indexMax3.add(i);
       	    		if(diffMaxRd3.size()==0){
       	    			diffMaxRd3.add(roadType3.get(i));
       	    		}
       	    		if(diffMaxRd3.size()>=1){
       	    			int diff = 1;
            		    for(int k=0;k<diffMaxRd3.size();k++){
                    		if(!diffMaxRd3.get(k).equals(roadType3.get(i)))
                     		   diff *= 1;
                    		else
                    		   diff *= 0;
            		    }
            		    if(diff == 1)
            		        diffMaxRd3.add(roadType3.get(i));
       	    		} 
       	    	}	
       	    }
   	          //find max count
   	        for(int i=0;i<distMon4Num.length;i++){
   	    	   if(distMon4Num[i]>maxNum4){
   	    		  maxNum4 = distMon4Num[i];
   	        	}	
   	        }
   	          //check more than one road has max count
     	    for(int i=0;i<distMon4Num.length;i++){
     	    	if(distMon4Num[i] == maxNum4){
     	    		indexMax4.add(i);
     	    		if(diffMaxRd4.size()==0){
     	    			diffMaxRd4.add(roadType4.get(i));
     	    		}
     	    		if(diffMaxRd4.size()>=1){
     	    			int diff = 1;
          		    for(int k=0;k<diffMaxRd4.size();k++){
                  		if(!diffMaxRd4.get(k).equals(roadType4.get(i)))
                   		   diff *= 1;
                  		else
                  		   diff *= 0;
          		    }
          		    if(diff == 1)
          		        diffMaxRd4.add(roadType4.get(i));
     	    		} 
     	    	}	
     	    }       	    
     	      // find max of all
     	    if(maxNum1 > maxOfAll){
     	    	maxOfAll = maxNum1;
     	    	whichIsMax = 1;
     	    }
     	    if(maxNum2 > maxOfAll){
     	    	maxOfAll = maxNum2;
     	    	whichIsMax = 2;
     	    }
     	    if(maxNum3 > maxOfAll){
     	    	maxOfAll = maxNum3;
     	    	whichIsMax = 3;
     	    }
    	    if(maxNum4 > maxOfAll){
     	    	maxOfAll = maxNum4;
     	    	whichIsMax = 4;
     	    }
     	    
     	    switch(whichIsMax){
     	        case 1:{
     	        	for(int k=0;k<diffMaxRd1.size();k++){
     	   	    	   int maxPrice = priceType1.get(indexMax1.get(0));  
     		    	   int minPrice = priceType1.get(indexMax1.get(0));
     	    	       for(int i=0;i<indexMax1.size();i++){
     	    	    	  if(roadType1.get(indexMax1.get(i)).equals(diffMaxRd1.get(k))){
     	    		         if(priceType1.get(indexMax1.get(i)) > maxPrice)
     	    		        	 maxPrice = priceType1.get(indexMax1.get(i));
     	    		    	 if(priceType1.get(indexMax1.get(i)) < minPrice)
     	    		    	     minPrice = priceType1.get(indexMax1.get(i));
     	    	    	  }
     	    	       }
     	    	       System.out.print(diffMaxRd1.get(k)+",  最高成交價:"+maxPrice+",  最低成交價:"+minPrice+"\n");
     	        	}
     	        	break;
     	        }
     	        case 2:{
     	        	for(int k=0;k<diffMaxRd2.size();k++){
     	   	    	   int maxPrice = priceType2.get(indexMax2.get(0));  
     		    	   int minPrice = priceType2.get(indexMax2.get(0));
     	    	       for(int i=0;i<indexMax2.size();i++){
     	    	    	  if(roadType2.get(indexMax2.get(i)).equals(diffMaxRd2.get(k))){
     	    		         if(priceType2.get(indexMax2.get(i)) > maxPrice)
     	    		        	 maxPrice = priceType2.get(indexMax2.get(i));
     	    		    	 if(priceType2.get(indexMax2.get(i)) < minPrice)
     	    		    	     minPrice = priceType2.get(indexMax2.get(i));
     	    	    	  }
     	    	       }
     	    	       System.out.print(diffMaxRd2.get(k)+",  最高成交價:"+maxPrice+",  最低成交價:"+minPrice+"\n");
     	        	}
     	        	break;
     	        }
     	        case 3:{
     	        	for(int k=0;k<diffMaxRd3.size();k++){
     	   	    	   int maxPrice = priceType3.get(indexMax3.get(0));  
     		    	   int minPrice = priceType3.get(indexMax3.get(0));
     	    	       for(int i=0;i<indexMax3.size();i++){
     	    	    	  if(roadType3.get(indexMax3.get(i)).equals(diffMaxRd3.get(k))){
     	    	    		 System.out.println("price : "+priceType3.get(indexMax3.get(i)));
     	    		         if(priceType3.get(indexMax3.get(i)) > maxPrice)
     	    		        	 maxPrice = priceType3.get(indexMax3.get(i));
     	    		    	 if(priceType3.get(indexMax3.get(i)) < minPrice)
     	    		    	     minPrice = priceType3.get(indexMax3.get(i));
     	    	    	  }
     	    	       }
     	    	       System.out.print(diffMaxRd3.get(k)+",  最高成交價:"+maxPrice+",  最低成交價:"+minPrice+"\n");
     	        	}
     	        	break;
     	        }
     	        case 4:{
     	        	for(int k=0;k<diffMaxRd4.size();k++){
     	   	    	   int maxPrice = priceType4.get(indexMax4.get(0));  
     		    	   int minPrice = priceType4.get(indexMax4.get(0));
     	    	       for(int i=0;i<indexMax4.size();i++){
     	    	    	  if(roadType4.get(indexMax4.get(i)).equals(diffMaxRd4.get(k))){
     	    	    		 System.out.println("price : "+priceType4.get(indexMax4.get(i)));
     	    		         if(priceType4.get(indexMax4.get(i)) > maxPrice)
     	    		        	 maxPrice = priceType4.get(indexMax4.get(i));
     	    		    	 if(priceType4.get(indexMax4.get(i)) < minPrice)
     	    		    	     minPrice = priceType4.get(indexMax4.get(i));
     	    	    	  }
     	    	       }
     	    	       System.out.print(diffMaxRd4.get(k)+",  最高成交價:"+maxPrice+",  最低成交價:"+minPrice+"\n");
     	        	}
     	        	break;
     	        }
     	    }
	        in.close();
	    }
}

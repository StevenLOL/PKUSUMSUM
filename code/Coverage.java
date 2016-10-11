package code;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


/**
 * args[0]:The path of the input.
 * 		Single-document summarization task: The path of the input file and this file can only contain one document you want to get the summary from.
 * 		Multi-document summarization task or topic-based multi-document summarization task: The path of the input directory and this directory can only contain one document set you want to get the summary from.
 * args[1]:The path of the output file and one file only contains one summary.
 * args[2]:The language of the document. 1: Chinese, 2: English, 3: other Western languages
 * args[3]:Specify which task to do.
 * 		1: single-document summarization, 2: multi-document summarization,
 * 		3: topic-based multi-document summarization
 * args[4]:The expected number of words in summary.
 * args[5]:Choose if you want to stem the input. (Only for English document) 
 * 		1: stem, 2: no stem, default = 1
 * args[6]:Choose whether you need remove the stop words.
 * 		If you need remove the stop words, you should input the path of stop word list. 
 *  	Or we have prepared an English stop words list as file ��stopword_Eng��, you can use it by input ��y��.
 *   	If you don��t need remove the stop words, please input ��n��.
 * 
 * */

public class Coverage {
	public doc myDoc = new doc();
    public ArrayList<Integer> summary_id = new ArrayList<>();
    public int sumNum = 0;
    
    public void Summarize(String args[]) throws IOException
    {
    	if(args[3].equals("1")){
			System.out.println("The Coverage method can't solve single-document summarization task.");
			return;
		}
    	
    	/* Read files */
    	File myfile = new File(args[0]);
    	myDoc.maxlen = Integer.parseInt(args[4]);
        myDoc.readfile(myfile.list(),args[0],args[2],args[6]);
        
        /* Get abstract */    
    	int tmpf = 0, tmpS = 0;
    	int sNum = 0;
        while(sumNum <= myDoc.maxlen && sNum < myDoc.snum) {
        	tmpf = sNum % myDoc.fnum;
        	tmpS = sNum / myDoc.fnum;
        	summary_id.add(myDoc.l_range[tmpf] + tmpS);
        	sumNum += myDoc.sen_len.get(myDoc.l_range[tmpf] + tmpS);
        	sNum++;
        }
    	
    	//output the abstract
    	File outfile = new File(args[1]);
        BufferedWriter bf = new BufferedWriter(new PrintWriter(outfile));
        for (int i : summary_id){
            //System.out.println(myDoc.original_sen.get(i));
            bf.append(myDoc.original_sen.get(i));
            bf.append("\n");
        }
        bf.close();
    }
}

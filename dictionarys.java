import java.io.*;
import java.util.*;
import java.awt.event.*;  
import java.awt.*;  
import javax.swing.*; 
public class dictionarys
{
	public static void main(String[] args) throws IOException
	{
		dictionaryGui dicGui=new dictionaryGui("Dictionary");
		dicGui.b1.setEnabled(false);
		dicGui.b2.setEnabled(false);
		dicGui.b3.setEnabled(true);
		work w=new work();
		BufferedReader fin = null;
		try
		{
			fin = new BufferedReader(new FileReader("dictionary.txt"));
			String ch;
			String word;
			int i = 0;
			while((ch = fin.readLine()) != null)
			{
				System.out.println(ch);
				word=ch;
				String[] mean=new String[2];
				mean=word.split("\\|");
				System.out.println(mean[0] + mean[1]);
				w.ADD(mean[0],mean[1]);
			}
		}
		catch(IOException e)
		{	
			System.out.println(e);
		}
		finally
		{
			if(fin != null)
				fin.close();
		}
		int i=1;
		while(i>0)
		{
			if(dicGui.Add())
					{
						String as=dicGui.s1;String sa=dicGui.s2;
						w.check(as,sa);
						w.ADD(as,sa);
						dicGui.popUp("ADDED");
						dicGui.b1.setEnabled(false);
						dicGui.addWord.setText("Enter the Word");
						dicGui.addMeaning.setText("Enter the Meaning");
						dicGui.t1=false;
					}
			if(dicGui.setWord())
					{
						String as=dicGui.s3;
						dicGui.popUp(w.Meaning(as));
						dicGui.b2.setEnabled(false);
						dicGui.MeaningWord.setText("Enter Word To Get Meaning");
						dicGui.t2=false;
					}
			if(dicGui.Meanings())
					{
						dicGui.enlist.setText(w.ENLIST());
						dicGui.t3=false;
						dicGui.bb=false;
					}
		}
	}				
}
class work
{
	dictionaryGui d=new dictionaryGui();
	node head=null;
	void ADD(String name,String meaning)
	{	
		node p=new node(name,meaning,null,null);
		boolean done=false;
		node q=head;
		if(q==null){head=p;}
		else
		{
			for(int i=0;i<name.length() && done==false;)
			{
				if(i>=q.getName().length())
				{
					i=0;
					if(q.getNext()!=null)
						q=q.getNext();
					else
					{
						p.setPrev(q);
						p.setNext(q.getNext());
						if(q.getNext()==null){q.setNext(p);break;}
						q.getNext().setPrev(p);
						q.setNext(p);
					}
				}
				else if(name.charAt(i)>q.getName().charAt(i))
				{
					if(q.getNext()==null){q.setNext(p);p.setPrev(q);done=true;}
					else
						q=q.getNext();
				}
				else if(name.charAt(i)<q.getName().charAt(i))
				{
					p.setNext(q);
					p.setPrev(q.getPrev());
					if(q.getPrev()==null){head=p;head.getNext().setPrev(p);break;}
					q.getPrev().setNext(p);
					q.setPrev(p);
					done=true;
				}
				else if(name.charAt(i)==q.getName().charAt(i))
				{
					i++;
					if(i==name.length())
					{
						p.setNext(q);
						p.setPrev(q.getPrev());
						if(q.getPrev()==null){head=p;head.getNext().setPrev(p);break;}
						q.getPrev().setNext(p);
						q.setPrev(p);
						done=true;
					}
				}
			}
		}
	}
	String ENLIST()
	{
		String s="";
		node q=head;
		if(q==null){return s="NO WORDS IN DICTIONARY";}
		else
		{
			while(q!=null)
			{
				s+=q.getName()+":"+q.getMeaning()+"\n";
				q=q.getNext();
			}
		}	
		return s;
	}
	String Meaning(String name)
	{
		String t="";
		node q=head;
		if(q==null){d.popUp("No such entry");}
		else
		{
			while(q!=null)
			{
				if(q.getName().equals(name))
				{
					t=q.getMeaning();return t;
				}
				q=q.getNext();
			}
		}
		return t;
	}
	void check(String name,String meaning)
	{
		int i=0;
		BufferedReader fin = null;
		String[] mean=new String[2];
		try
		{
			fin = new BufferedReader(new FileReader("dictionary.txt"));
			String ch;
			String word;
			while((ch = fin.readLine()) != null)
			{
				word=ch;
				mean=word.split("\\|");
				if(mean[0].equals(name) && mean[1].equals(meaning))
				{
					i++;
				}
			}
			if(i==0)
			{
				fin.close();
				this.cdo(name,meaning);
			}			
		}
		catch(IOException e)
		{	

			System.out.println(e);
		}
	}
	BufferedWriter fout=null;
	void cdo(String name,String meaning) 
	{
		try
		{
			fout = new BufferedWriter(new FileWriter("dictionary.txt",true));
			fout.write(name + "|" + meaning);
			fout.newLine();
			fout.flush();
		}			
		catch(IOException e)
		{	
			System.out.println(e);
		}
	}
}
class node
{
	node next;
	node prev;
	String name;
	String meaning;
	public node(String name,String meaning,node next,node prev)
	{
		this.name=name;
		this.meaning=meaning;
		this.next=next;
		this.prev=prev;
	}
	public String getName()
	{
		return this.name;
	}
	public String getMeaning()
	{
		return this.meaning;
	}
	public node getNext()
	{
		return this.next;
	}
	public node getPrev()
	{
		return this.prev;
	}
	public void setName(String name)
	{
		this.name=name;
	}
	public void setMeaning(String meaning)
	{
		this.meaning=meaning;
	}
	public void setNext(node next)
	{
		this.next=next;
	}
	public void setPrev(node prev)
	{
		this.prev=prev;
	}
}

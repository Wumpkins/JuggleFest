import java.io.*;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Scanner;


public class JuggleFest {
	
	public static void main (String args[]) throws FileNotFoundException{
		Scanner input = new Scanner(new File(args[0]));
		
		ArrayList<Circuit> circuits = new ArrayList<Circuit>();
		ArrayList<Juggler> jugglers = new ArrayList<Juggler>();
		ArrayList<Juggler> noPref = new ArrayList<Juggler>();
		ArrayList<Circuit> empty = new ArrayList<Circuit>();
		
		JuggleFest me = new JuggleFest();
		me.parse(input, circuits, jugglers);	
		
		int size = jugglers.size()/circuits.size();
		
		for(Circuit c:circuits)
			c.initList(size);
		
		for(Juggler j: jugglers)
			j.findPref(circuits, noPref);

		for(Circuit c: circuits){
			if(!c.isFull())
				empty.add(c);
		}
		
		me.findNoPref(empty, noPref);
		me.print(circuits, jugglers);
		//System.out.println("done");
	}
		
	class Juggler{
		int h, e, p;
		String name;
		
		ArrayList <Integer>pref;
		
		int current = 0;
		
		Juggler(int h1, int e1, int p1, String n, ArrayList<Integer> arr){
			h = h1;
			e = e1;
			p = p1;
			name = n;
			pref = arr;
		}

		void findPref(ArrayList<Circuit> circuits, ArrayList<Juggler> noPref){
			if(current>=pref.size()){
				noPref.add(this);
			}
			else{
			Circuit c = circuits.get(pref.get(current));
				if(!c.addJugg(this, circuits, noPref)){
					current++;
					this.findPref(circuits, noPref);
				}
			}
		}
		
		public String toString(){
			return name;
		}
		
		int getScore(Circuit c){
			int score = h*c.h + e*c.e + p*c.p;	
			return score;
		}
	}
	
	class Circuit{
		int h, e, p, size;
		String name;
		ArrayList<Juggler> list; 
			
		Circuit(int h1, int e1, int p1, String n){
			h = h1;
			e = e1;
			p = p1;
			name = n;
		}
		
		void initList(int size){
			list = new ArrayList<Juggler> ();
			this.size = size;
		}
		
		boolean isFull(){
			if (list.size()==size)
				return true;
			return false;
		}
		
		//returns true if added successfully
		boolean addJugg(Juggler j, ArrayList<Circuit> circuits, ArrayList<Juggler> noPref){
			if(this.isFull()){
				if(j.getScore(this)<=list.get(0).getScore(this))
					return false;
				else{
					Juggler out = list.remove(0);
					list.add(j);
					this.sort();
					out.current++;
					out.findPref(circuits, noPref);
					return true;
				}
			}
			list.add(j);
			this.sort();
			return true;
		}
		
		void sort(){
			int n = list.size();
			for(int m = n; m>=0; m--){
				for(int i = 0; i<n-1; i++){
					int k = i+1;
					if(list.get(i).getScore(this)>list.get(k).getScore(this))
						Collections.swap(list, i, k);
				}				
			}			
		}
		
		public String toString(){
			return name;
		}
	}
	
	//assigns jugglers to circuits randomly, since these are all nonpreference
	void findNoPref(ArrayList<Circuit> empty, ArrayList<Juggler> noPref){
		for(Circuit c: empty)
			while(!c.isFull())
				c.list.add(noPref.remove(0));
	}
	
	void print(ArrayList<Circuit> circuits, ArrayList<Juggler> jugglers){
		
		Writer writer = null;
		try {
		    writer = new BufferedWriter(new OutputStreamWriter(
		          new FileOutputStream("Output.txt"), "utf-8"));
			for(Circuit c:circuits){
				String str = "";
				str += c + " ";
				for(Juggler j: c.list){
					str += j + " ";
					for(int i : j.pref){
						str+=circuits.get(i) + ":" + j.getScore(circuits.get(i)) + " ";
					}
				}
				str += "\r\n";
				writer.write(str);
			}
		} catch (IOException ex) {
		  System.out.println("Error in output");
		} finally {
		   try {writer.close();} catch (Exception ex) {}
		}
	}
	
	void parse(Scanner input, ArrayList<Circuit> circuits, ArrayList<Juggler> jugglers){
		boolean circuit = true;
		while (input.hasNext()){
			String clean = input.nextLine();
			clean = clean.replace(':', ' ');
			
			if(clean.isEmpty())
				circuit=false;
			else if(circuit){
				Scanner parse = new Scanner(clean);
				parse.next();		
				String name = parse.next();
				parse.next();
				int h = parse.nextInt();
				parse.next();
				int e = parse.nextInt();
				parse.next();
				int p = parse.nextInt();
					
				circuits.add(new Circuit(h, e, p, name));	
				parse.close();
			}
			else {
				clean = clean.replace('C', ' ');
				clean = clean.replace(',', ' ');
				Scanner parse = new Scanner(clean);
				parse.next();
				String name = parse.next();
				parse.next();
				int h = parse.nextInt();
				parse.next();
				int e = parse.nextInt();
				parse.next();
				int p = parse.nextInt();
				
				ArrayList<Integer> arr = new ArrayList<Integer>();
				
				while (parse.hasNext())
					arr.add(parse.nextInt());
					
				jugglers.add(new Juggler(h, e, p, name, arr));
				parse.close();
			}
			//test case System.out.println(clean);
		}
		
	
	}
}
	


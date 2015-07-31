package mainClient;


import java.rmi.Naming;
import java.rmi.RemoteException;

import javax.swing.SwingUtilities;

import frames.AdminFrame;
import frames.AuthFrame;
import frames.StudAuthFrame;
import frames.ZamDirectPRFrame;
import frames.ZamDirectURFrame;
import frames.ZavAvtodeloFrame;

import rmi.AutoschoolRmiInterface;

public class MainClient {
	
	public static void main(String... args) throws Exception {
		
		
		final AutoschoolRmiInterface clin = (AutoschoolRmiInterface)Naming.lookup("rmi://192.168.61.1"  + ":" + 1099 + "/AutoschoolService");
		//final AutoschoolRmiInterface clin = (AutoschoolRmiInterface)Naming.lookup("rmi://localhost"  + ":" + 1099 + "/AutoschoolService");
		/*Students st = new Students();
		st.setName("Ваня"); 
		st.setSurname("Dfd");
		List<Groups> gr = null;
		try {
			clin.insertStudent(st);
			//gr = (List<Groups>) clin.getGroupList();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		/*for (TypeEducation cat : tp) {
			System.out.println(cat.getName());
		}
		if( gr.isEmpty()){ System.out.println("!");}
		/*List<Students> stud = Collections.synchronizedList(new ArrayList<Students>());
		List<YearOfEducation> stud1 = Collections.synchronizedList(new ArrayList<YearOfEducation>());;
		try {
			stud = clin.getStudentsFromGroup(7);
			stud1 = (List<YearOfEducation>) clin.getYearOfEducation();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			for (Students cat : stud) {
				System.out.println(cat.getName()+cat.getSurname());
			}
			if( stud1.isEmpty()){ System.out.println("Fucking shit");}*/
			
			SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				AuthFrame inst;
				inst = new AuthFrame(clin);
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				/*try {
					clin.getDriveStat();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			}
		});
	
		
		

}}

package model;

public class StartUp {

        public static void main(String[] args){
            UniLink unilinkOb = new UniLink();
            DAO dao = new DAO();
            DbConnection connectionOb = new DbConnection();
            if(connectionOb.openConnection()) {
                System.out.println("Opened datasource");
                unilinkOb.startProgram();
                return;

            }
            connectionOb.closeConnection();
        }

}

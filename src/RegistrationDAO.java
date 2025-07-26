public interface RegistrationDAO {
    void setUserDetails(int userid,int age,String gender,float height,float weight,String level);

    int setUserLogin(String name, String pass);
}

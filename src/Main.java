public class Main {
    public static void main(String[] args) {
        FacePamphletDatabase database = new FacePamphletDatabase();
        FacePamphletProfile profile = new FacePamphletProfile("zura");
        database.addProfile(profile);
        System.out.println(database.profiles);
        FacePamphletProfile profile2 = profile;
        profile2.setStatus("vaime");
        System.out.println(database.profiles);
    }
}

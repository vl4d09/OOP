public class Main {
    public static void main(String[] args) {
        String folderPath = "C:\\Users\\VLAD'S LAPTOP\\Desktop\\OOP\\lab2\\Files"; 
        Interface applicationBehaviour = new Interface(folderPath);
        applicationBehaviour.run();
    }
}

public class App {
    public static void main(String[] args) throws Exception {
        ArvoreAVL arvoreAVL = new ArvoreAVL();

        arvoreAVL.inserir(7);
        arvoreAVL.inserir(5);
        arvoreAVL.inserir(25);
        arvoreAVL.inserir(3);
        arvoreAVL.inserir(10);
        arvoreAVL.inserir(50);
        arvoreAVL.inserir(8);
        arvoreAVL.inserir(20);
        arvoreAVL.inserir(30);
        arvoreAVL.inserir(1);
        
        System.out.println('\n'+arvoreAVL.imprimir());
    }
}

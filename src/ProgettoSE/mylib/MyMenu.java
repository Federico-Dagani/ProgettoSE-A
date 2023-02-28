package ProgettoSE.mylib;

/*
Questa classe rappresenta un menu testuale generico a piu' voci
Si suppone che la voce per uscire sia sempre associata alla scelta 0 
e sia presentata in fondo al menu

*/
public class MyMenu {
    final private static String CORNICE_ANGOLO_SX_SUP = "┌";
    final private static String CORNICE_ANGOLO_DX_SUP = "┐";
    final private static String CORNICE_ANGOLO_SX_INF = "└";
    final private static String CORNICE_ANGOLO_DX_INF = "┘";
    final private static String CORNICE_CONTORNO_ORIZZ = "-";
    final private static String CORNICE_CONTORNO_VERT = "|";
    final private static String FRECCETTA = "→";//⟼
    final private static String VOCE_USCITA = "0 → Esci";
    final private static String RICHIESTA_INSERIMENTO = "Digita il numero dell'opzione desiderata > ";

    private String titolo;
    private String[] voci;
    private int lunghezza_cornice_orizz;


    public MyMenu(String titolo, String[] voci) {
        this.titolo = titolo;
        this.voci = voci;
        this.lunghezza_cornice_orizz = this.titolo.length();
    }

    public int scegliConUscita() {
        stampaMenuConUscita();
        return InputDati.leggiIntero(RICHIESTA_INSERIMENTO, 0, voci.length);
    }

    public int scegliSenzaUscita() {
        stampaMenuSenzaUscita();
        return InputDati.leggiIntero(RICHIESTA_INSERIMENTO, 0, voci.length);
    }

    public void stampaMenuConUscita() {
        String cornice_sup = "";
        cornice_sup += CORNICE_ANGOLO_SX_SUP;
        cornice_sup += generaCorniceSenzaAngolo();
        cornice_sup += CORNICE_ANGOLO_DX_SUP;
        String cornice_inf = "";
        cornice_inf += CORNICE_ANGOLO_SX_INF;
        cornice_inf += generaCorniceSenzaAngolo();
        cornice_inf += CORNICE_ANGOLO_DX_INF;
        System.out.println(cornice_sup);
        System.out.println(generaRigaCentrale());
        System.out.println(cornice_inf);
        System.out.println(cornice_sup);
        for (int i = 0; i < voci.length; i++) {
            System.out.println(generaVoce((i + 1) + " " + FRECCETTA + " " + voci[i]));
        }
        System.out.println(generaVoce((0) + " " + FRECCETTA + " " + "esci"));
        System.out.println(cornice_inf);
    }

    public void stampaMenuSenzaUscita() {
        String cornice_sup = "";
        cornice_sup += CORNICE_ANGOLO_SX_SUP;
        cornice_sup += generaCorniceSenzaAngolo() ;
        cornice_sup += CORNICE_ANGOLO_DX_SUP;
        String cornice_inf = "";
        cornice_inf += CORNICE_ANGOLO_SX_INF;
        cornice_inf += generaCorniceSenzaAngolo();
        cornice_inf += CORNICE_ANGOLO_DX_INF;
        System.out.println(cornice_sup);
        System.out.println(generaRigaCentrale());
        System.out.println(cornice_inf);
        System.out.println(cornice_sup);
        for (int i = 0; i < voci.length; i++) {
            System.out.println(generaVoce(FRECCETTA + " " + voci[i]));
        }
        System.out.println(cornice_inf);
        System.out.println();
    }

    private String generaCorniceSenzaAngolo() {
        String cornice = "";
        for (int i = 0; i < titolo.length(); i++) {
            cornice += CORNICE_CONTORNO_ORIZZ;
            cornice += CORNICE_CONTORNO_ORIZZ;
        }
        lunghezza_cornice_orizz = titolo.length() * 2 + 2;
        return cornice;
    }

    private String generaRigaCentrale() {
        char[] riga_centrale = new char[lunghezza_cornice_orizz];
        riga_centrale[0] = CORNICE_CONTORNO_VERT.charAt(0);
        riga_centrale[lunghezza_cornice_orizz - 1] = CORNICE_CONTORNO_VERT.charAt(0);
        int caratteri_vuoti_meta = ((lunghezza_cornice_orizz - 2) - titolo.length()) / 2 + 1;
        int c = 0;
        for (int i = caratteri_vuoti_meta; i < (titolo.length() + caratteri_vuoti_meta); i++) {
            riga_centrale[i] = titolo.charAt(c);
            c++;
        }
        for (int i = 0; i < lunghezza_cornice_orizz; i++) {
            if (riga_centrale[i] == Character.MIN_VALUE) {
                riga_centrale[i] = ' ';
            }
        }
        return new String(riga_centrale);
    }

    private String generaVoce(String voce) {
        char[] voce_finale_char = new char[lunghezza_cornice_orizz];
        voce_finale_char[0] = CORNICE_CONTORNO_VERT.charAt(0);
        voce_finale_char[lunghezza_cornice_orizz - 1] = CORNICE_CONTORNO_VERT.charAt(0);
        char[] voce_char = new char[voce.length()];
        int c = 0;
        for (int i = 1; i < voce_char.length + 1; i++) {
            voce_finale_char[i] = voce.charAt(c);
            c++;
        }
        for (int i = 0; i < lunghezza_cornice_orizz; i++) {
            if (voce_finale_char[i] == Character.MIN_VALUE) {
                voce_finale_char[i] = ' ';
            }
        }
        return new String(voce_finale_char);
    }

}


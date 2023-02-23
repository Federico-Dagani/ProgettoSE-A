package ProgettoSE.mylib;

public class ControlloDati {

    /**
     * Metodo che controlla se il carattere passato è una vocale
     * @param lettera da controllare
     * @return boolean
     */
    public static boolean seVocale(char lettera) {
        return lettera == 'A' || lettera == 'E' || lettera == 'I' || lettera == 'O' || lettera == 'U' ||
                lettera == 'a' || lettera == 'e' || lettera == 'i' || lettera == 'o' || lettera == 'u';
    }

    /**
     * Metodo che controlla se il carattere passato è una consonante
     * @param lettera da controllare
     * @return boolean
     */
    public static boolean seConsonante(char lettera){
        return lettera == 'B' || lettera == 'C' || lettera == 'D' || lettera == 'F' || lettera == 'G' || lettera == 'H' || lettera == 'J' || lettera == 'K' || lettera == 'L' || lettera == 'M' || lettera == 'N' || lettera == 'P' || lettera == 'Q' || lettera == 'R' || lettera == 'S' || lettera == 'T' || lettera == 'V' || lettera == 'W' || lettera == 'X' || lettera == 'Y' || lettera == 'Z' ||
                lettera == 'b' || lettera == 'c' || lettera == 'd' || lettera == 'f' || lettera == 'g' || lettera == 'h' || lettera == 'j' || lettera == 'k' || lettera == 'l' || lettera == 'm' || lettera == 'n' || lettera == 'p' || lettera == 'q' || lettera == 'r' || lettera == 's' || lettera == 't' || lettera == 'v' || lettera == 'w' || lettera == 'x' || lettera == 'y' || lettera == 'z';
    }

    /**
     * Metodo che controlla se il carattere passato è un numero
     * @param carattere da controllare
     * @return boolean
     */
    public static boolean seNumero(char carattere){
        return Character.isDigit(carattere);
    }
}

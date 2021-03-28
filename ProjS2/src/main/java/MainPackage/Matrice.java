/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage;

import java.util.Arrays;

/**
 *
 * @author francois
 */
public class Matrice {

    private int nbrLig;
    private int nbrCol;
    private double[][] coeffs;

    public Matrice(int nl, int nc) {
        this.nbrLig = nl;
        this.nbrCol = nc;
        this.coeffs = new double[nl][nc];
        for (int i = 0; i < this.nbrLig; i++) {
            for (int j = 0; j < this.nbrCol; j++) {
                this.coeffs[i][j] = 0;
            }
        }
    }

    public String toString() {
        String s;
        s = "";
        for (int i = 0; i < this.nbrLig; i++) {
            s = s + "[";
            for (int j = 0; j < this.nbrCol; j++) {
                s = s + String.format("%+4.2E", this.coeffs[i][j]);
                if (j != this.nbrCol - 1) {
                    s = s + " ";
                }

            }
            s = s + "]\n";

        }
        return s;
    }

    public static Matrice identite(int taille) {
        Matrice m = new Matrice(taille, taille);
        for (int i = 0; i < m.nbrLig; i++) {
            m.coeffs[i][i] = 1;
        }
        return m;
    }

    public static int aleaUnOuDeux() {
        return (int) (Math.random() * 2 + 1);
    }

    public static Matrice matAleaZeroUnDeux(int nbLig, int nbCol,
            double probaZero) {
        Matrice res;
        res = new Matrice(nbLig, nbCol);
        for (int i = 0; i < res.nbrLig; i++) {
            for (int j = 0; j < res.nbrCol; j++) {
                if (Math.random() > probaZero) {
                    res.coeffs[i][j] = Matrice.aleaUnOuDeux();
                }
            }
        }
        return res;
    }

    public static void testAffiche() {
        int i = 3;
        Matrice m = new Matrice(2, 3);
        m.coeffs[0][0] = 3.223456;
        System.out.println("coeffs de m : "
                + Arrays.deepToString(m.coeffs));
        System.out.println("m = \n" + m);

    }

    public static void testIdentite() {
        System.out.println("identité  4:");
        System.out.println(Matrice.identite(4).toString());
        System.out.println("identité 5 :");
        System.out.println(Matrice.identite(5).toString());

    }

    public static void test1() {
        Matrice m = Matrice.matAleaZeroUnDeux(4, 6, 0.33);
        System.out.println("mat alea : \n" + m);

    }
    
    public static Matrice matTest1() {
        Matrice res = new Matrice(3, 3);
        int cur = 0;
        for (int i = 0; i < res.nbrLig; i++) {
            for (int j = 0; j < res.nbrCol; j++) {
                res.coeffs[i][j] = cur;
                cur++;
            }
        }
        return res;
    }

    public int getNbrLig() {
        return this.nbrLig;
    }
    
        public int getNbrCol() {
        return this.nbrCol;
    }

    public double get(int lig, int col) {
        return this.coeffs[lig][col];
    }

        public void set(int lig, int col, double val) {
        this.coeffs[lig][col] = val;
    }
         
    public Matrice concatLig(Matrice n) {
        if (this.getNbrCol() != n.getNbrCol()) {
            throw new Error("nombre de cols incompatible");
        }
        Matrice res = new Matrice(this.getNbrLig() + n.getNbrLig(),
                this.getNbrCol());
        for (int i = 0; i < this.getNbrLig(); i++) {
            for (int j = 0; j < this.getNbrCol(); j++) {
                res.set(i, j, this.get(i, j));
            }
        }
        for (int i = 0; i < n.getNbrLig(); i++) {
            for (int j = 0; j < n.getNbrCol(); j++) {
                res.set(i + this.getNbrLig(), j, n.get(i, j));
            }
        }
        return res;
    }
    
    public Matrice concatCol(Matrice m2) {
        if (this.getNbrLig() != m2.getNbrLig()) {
            throw new Error("les matrices doivent avoir même nombre de lignes");
        }
        Matrice res = new Matrice(this.getNbrLig(), this.getNbrCol() + m2.getNbrCol());
        for (int i = 0; i < res.getNbrLig(); i++) {
            for (int j = 0; j < res.getNbrCol(); j++) {
                if (j < this.getNbrCol()) {
                    res.set(i, j, this.get(i, j));
                } else {
                    res.set(i, j, m2.get(i, j - this.getNbrCol()));
                }
            }
        }
        return res;
    }

    public static void testConcatLig() {
        Matrice m1 = Matrice.identite(3);
        Matrice m2 = Matrice.matAleaZeroUnDeux(2, 3, 0.33);
        Matrice m3 = m1.concatLig(m2);
        System.out.println("M1 : \n" + m1);
        System.out.println("M2 : \n" + m2);
        System.out.println("M3 = M1.concatLig(M2) : \n" + m3);
    }

        public static void test2(){
        System.out.println("----------- test 1 --------------");
        int nl = 4, nc = 6;
        double pz = 0.3;
        System.out.println("matrice alea de taille " + nl + " x " + nc
                + " (proba de zero : " + pz + ") : ");
        System.out.println(Matrice.matAleaZeroUnDeux(nl, nc, pz));
    }
        
        public Matrice subLignes(int ligMin, int ligMax) {
        if (ligMin < 0 || ligMax < ligMin || ligMax >= this.getNbrLig()) {
            throw new Error("indices lignes invalides");
        }
        Matrice res = new Matrice(ligMax - ligMin + 1, this.getNbrCol());
        for (int lig = 0; lig < res.getNbrLig(); lig++) {
            for (int col = 0; col < res.getNbrCol(); col++) {
                res.set(lig, col, this.get(ligMin + lig, col));
            }
        }
        return res;
    }
        
        public Matrice subCols(int colMin, int colMax) {
        if (colMin < 0 || colMax < colMin || colMax >= this.getNbrCol()) {
            throw new Error("indices colonnes invalides");
        }
        Matrice res = new Matrice(this.getNbrLig(), colMax - colMin + 1);
        for (int lig = 0; lig < res.getNbrLig(); lig++) {
            for (int col = 0; col < res.getNbrCol(); col++) {
                res.set(lig, col, this.get(lig, colMin + col));
            }
        }
        return res;
    }
        
    public Matrice copie() {
        return this.subLignes(0, this.getNbrLig() - 1);
    }
            
    public Matrice transposee() {
        Matrice res = new Matrice(this.getNbrCol(), this.getNbrLig());
        for (int i = 0; i < res.getNbrLig(); i++) {
            for (int j = 0; j < res.getNbrCol(); j++) {
                res.set(i, j, this.get(j, i));
            }
        }
        return res;
    }
    
    public Matrice metAuCarre() {
        return this.concatCol(Matrice.identite(this.getNbrLig())).concatLig(
                Matrice.identite(this.getNbrCol()).concatCol(this.transposee()));
    }

    public static int intAlea(int bmin, int bmax) {
        return (int) (Math.random() * (bmax - bmin + 1)) + bmin;
    }

    public static void test3() {
        System.out.println("----------- test 2 --------------");
        int nl = intAlea(1, 4), nc = intAlea(1, 4);
        double pz = 0.33;
        Matrice m = Matrice.matAleaZeroUnDeux(nl, nc, pz);
        Matrice mc = m.metAuCarre();
        Matrice mbis = mc.subLignes(0, m.getNbrLig() - 1).subCols(0, m.getNbrCol() - 1);
        System.out.println("m : matrice de base : ");
        System.out.println(m);
        System.out.println("mc : mise au carré : ");
        System.out.println(mc);
        System.out.println("mbis : (sub de mc) : ");
        System.out.println(mbis);
    }
        
    public Matrice add(Matrice m2) {
        if (this.getNbrLig() != m2.getNbrLig() || this.getNbrCol() != m2.getNbrCol()) {
            throw new Error("tailles incompatibles pour add");
        }
        Matrice res = new Matrice(this.getNbrLig(), this.getNbrCol());
        for (int i = 0; i < res.getNbrLig(); i++) {
            for (int j = 0; j < res.getNbrCol(); j++) {
                res.set(i, j, this.get(i, j) + m2.get(i, j));
            }
        }
        return res;
    }

    public Matrice opp() {
        Matrice res = new Matrice(this.getNbrLig(), this.getNbrCol());
        for (int i = 0; i < res.getNbrLig(); i++) {
            for (int j = 0; j < res.getNbrCol(); j++) {
                res.set(i, j, -this.get(i, j));
            }
        }
        return res;
    }

    public Matrice moins(Matrice m2) {
        return this.add(m2.opp());
    }

    public Matrice mult(Matrice m2) {
        if (this.getNbrCol() != m2.getNbrLig()) {
            throw new Error("tailles incompatibles pour mult");
        }
        Matrice res = new Matrice(this.getNbrLig(), m2.getNbrCol());
        for (int i = 0; i < res.getNbrLig(); i++) {
            for (int j = 0; j < res.getNbrCol(); j++) {
                double cur = 0;
                for (int k = 0; k < this.getNbrCol(); k++) {
                    cur = cur + this.get(i, k) * m2.get(k, j);
                }
                res.set(i, j, cur);
            }
        }
        return res;
    }

    public int permuteLigne(int n1,int n2){
        int signature = 1;
        for(int i=0; i<this.getNbrCol();i++){
            if (this.get( n1, i) != this.get(n2,i)){
                signature = -1;
            }
        }
        if (signature ==-1){
            double tampon=0;
            for(int i=0; i<this.getNbrCol();i++){
                tampon = this.get(n1,i);
                this.set(n1,i,this.get(n2,i));
                this.set(n2,i,tampon);
            }
        }
        return signature;
    }
    
    public void transvection(int i1, int i2){
        if (i1 > this.getNbrCol()) {
            throw new Error("incompatible");
        }
        double p = (this.get(i2,i1))/(this.get(i1,i1));
        if (this.get(i1,i1)==0){
            throw new Error ("Erreur 0");
        }
        for (int i=0; i<this.getNbrCol(); i++){
            if (i==i1){
                this.set(i2,i,0);
            }else{
                this.set(i2,i,this.get(i2,i)-p*this.get(i1,i));
            }
        }
    }

    public void testTransvection(){
         Matrice m= matTest1();
     System.out.println(m);
     m.permuteLigne(0, 1);
      System.out.println(m);
     m.transvection(0, 2);
      System.out.println(m);
    }
    
    public void RemplirMat (){
        for (int i = 0; i < this.nbrLig; i++) {
            for (int j = 0; j < this.nbrCol; j++) {
                System.out.println("veuillez rentrer la valeur colone "+(i+1)+" ligne "+(j+1)+" de la matrice ");
                this.coeffs[i][j]=Lire.d();
            }

        }

    }
    
    public double lignePlusGrandPivot (int e){
        double epsilon_pivot = 10^-8;
        int ligne = 0;
        double plusGrandPivot = Math.abs(this.get(0,e));
        for (int i=0; i<this.nbrLig; i++){
            if (Math.abs(this.get(i,e))> plusGrandPivot);
            ligne = i;
        }
        if (plusGrandPivot < epsilon_pivot){
            ligne = -1;
        }
        return ligne;
    }
    
    public static void testLignePlusGrandPivot(){
        Matrice m= matTest1();
     System.out.println(m);
     double k = m.lignePlusGrandPivot(2);
        System.out.println(k);
    }
    
public static void main(String[] args) {
        // testAffiche();
        //testIdentite();
        //test1()
     //testLignePlusGrandPivot();
    }
}
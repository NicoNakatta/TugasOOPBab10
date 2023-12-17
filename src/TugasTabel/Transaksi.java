/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TugasTabel;


/**
 *
 * @author ACER
 */
public class Transaksi {
    String Client, Smuggler;
    int Profit;
        
    int jualOrgan(int beli){
        Profit = Profit + beli * 5000000;
        return Profit;
    }
    int jualOrgan(int belij, int belig){
        Profit = Profit + belij *5000000 + belig * 1000000;
        return Profit;
    }
    int kembalian(int uang, int harga){
        return uang - harga;
    }
    public Transaksi(){
        this.Client = "Anon";
    }
}

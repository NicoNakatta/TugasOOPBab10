/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TugasTabel3;


/**
 *
 * @author ACER
 */
public class Transaksi {
    String Client, Smuggler;
    int Profit;

    int jualOrgan(TransaksiGinjal ginjal){
        Profit = ginjal.harga;
        return Profit;
    }
    int jualOrgan(TransaksiJantung jantung){
        Profit = jantung.harga;
        return Profit;
    }
    public Transaksi(){
        this.Client = "Anon";
    }
}

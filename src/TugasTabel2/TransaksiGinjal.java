/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TugasTabel2;


/**
 *
 * @author ACER
 */
public class TransaksiGinjal extends Transaksi implements Jualan{
    public int jualOrgan(int beli){
        Profit = Profit + beli * 1000000;
        return Profit;
    }
}

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
public class TransaksiGinjal extends Transaksi{
    @Override
    int jualOrgan(int beli){
        Profit = Profit + beli * 1000000;
        return Profit;
    }
}

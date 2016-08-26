/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calc;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mlima
 */
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String calc = request.getParameter("stringCalc");
            request.setAttribute("stringCalc", calc);            

            List<String> convertedCalc = new ArrayList<>();
            try {
                BracerParser bp = new BracerParser(0);
                bp.parse(calc);
                convertedCalc = bp.toInverse(bp.getStackRPN().toArray());
            } catch (ParseException ex) {
                Logger.getLogger(MainServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            MipsParser mp = new MipsParser();
            String codigoRetorno = mp.parseToMips(convertedCalc);

            request.setAttribute("codigoRetorno", codigoRetorno);
        } catch (Exception e) {
            request.setAttribute("codigoRetorno", "Calculo Inválido");
        }        
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}

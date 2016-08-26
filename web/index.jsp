<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Calc</title>

        <link rel="stylesheet" type="text/css" href="resources/bootstrap.min.css"/>
        <style>
            .no-padd {
                padding: 0;
            }
            .btn-calc {
                width: 100%;
                text-align: center;
                height: 60px;
                padding-top: 16px;
                border: 1px solid #EEE;
                background: #DDD;
                color: #555;
                font-size: 20px;
                font-weight: 600;   
                cursor: pointer;
            }

            .btn-calc:hover {
                border-color: #999;
                background: #BBB; 
            }

            .btn-calc-esp {
                border: 1px solid #ddd;
                background: #ccc;
                color: #555;                
                font-size: 16px;
            }
            .btn-esp {
                width: 100%;
                height: 59px;
                padding-left: 0;
                padding-right: 0;
            }

        </style>
    </head>
    <body>
        <nav class="navbar navbar-default navbar-inverse">
            <div class="container-fluid">
                <div class="container">
                    <h3 style="color: #CCC; margin-top: 11px"><b>CALC TO MIPS</b> - Marco Antonio de Lima</h3>
                </div>
            </div>
        </nav>
        <div class="container">
            <div class="col-xs-12 no-padd">
                <form id="formulario" action="MainServlet" method="POST">
                    <div class="col-md-6 no-padd">
                        <div class="form-group">
                            <label for="stringCalc">Cálculo</label>
                            <input type="text" id="stringCalc" class="form-control" name="stringCalc" value="<%out.println(request.getAttribute("stringCalc") == null ? "" : request.getAttribute("stringCalc"));%>">
                        </div>

                        <div class="col-xs-2 no-padd"><div class="btn-calc">1</div></div>
                        <div class="col-xs-2 no-padd"><div class="btn-calc">2</div></div>
                        <div class="col-xs-2 no-padd"><div class="btn-calc">3</div></div>
                        <div class="col-xs-2 no-padd"><div class="btn-calc">+</div></div>
                        <div class="col-xs-4 no-padd"><div class="btn-calc btn-calc-esp">fat(</div></div>
                        <div class="col-xs-2 no-padd"><div class="btn-calc">4</div></div>
                        <div class="col-xs-2 no-padd"><div class="btn-calc">5</div></div>
                        <div class="col-xs-2 no-padd"><div class="btn-calc">6</div></div>
                        <div class="col-xs-2 no-padd"><div class="btn-calc">-</div></div>
                        <div class="col-xs-4 no-padd"><div class="btn-calc btn-calc-esp">sqrt(</div></div>
                        <div class="col-xs-2 no-padd"><div class="btn-calc">7</div></div>
                        <div class="col-xs-2 no-padd"><div class="btn-calc">8</div></div>
                        <div class="col-xs-2 no-padd"><div class="btn-calc">9</div></div>
                        <div class="col-xs-2 no-padd"><div class="btn-calc">x</div></div>
                        <div class="col-xs-4 no-padd"><div class="btn-calc btn-calc-esp">fib(</div></div>
                        <div class="col-xs-2 no-padd"><div class="btn-calc">0</div></div>
                        <div class="col-xs-2 no-padd"><div class="btn-calc">(</div></div>
                        <div class="col-xs-2 no-padd"><div class="btn-calc">)</div></div>
                        <div class="col-xs-2 no-padd"><div class="btn-calc">/</div></div>
                        <div class="col-xs-4 no-padd">
                            <div class="col-xs-6 no-padd"><button class="btn btn-danger btn-esp" type="button" onclick="limpar()">Limpar</button></div>
                            <div class="col-xs-6 no-padd"><input id="submit" type="submit" class="btn btn-primary btn-esp" value="Gerar"/></div>
                        </div>
                    </div>                    
                </form>

                <div class="clearfix"></div>
                <hr>
                <div class="col-md-6 no-padd">
                    <div class="col-xs-3 no-padd"><button class="btn btn-block btn-default" onclick="exemplo(1);">Exemplo1</button></div>
                    <div class="col-xs-3 no-padd"><button class="btn btn-block btn-default" onclick="exemplo(2);">Exemplo2</button></div>
                    <div class="col-xs-3 no-padd"><button class="btn btn-block btn-default" onclick="exemplo(3);">Exemplo3</button></div>
                    <div class="col-xs-3 no-padd"><button class="btn btn-block btn-default" onclick="exemplo(4);">Exemplo4</button></div>
                </div>      
            </div>
        </div>
    </div>
    <hr>
    <div class="container">
        <h4><b>Código </b><a class="btn btn-info" onclick="copiar();">Copiar Código</a></h4><br>
        <pre><%out.println(request.getAttribute("codigoRetorno") == null ? "" : request.getAttribute("codigoRetorno"));%></pre>
    </div>    
</body>
<script src="resources/jquery-2.1.3.min.js"></script>
<script>
            $('#stringCalc').focus();
            $('.btn-calc').click(function () {
                console.log($(this).html());
                $('#stringCalc').val($('#stringCalc').val() + $(this).html()).focus();
            });
            function copiar() {
                var divACopiar = document.querySelector("pre");
                var range = document.createRange();
                range.selectNode(divACopiar);
                window.getSelection().addRange(range);
                document.execCommand("copy");
                $('#stringCalc').focus();
            }

            function limpar() {
                $('#stringCalc').val('');
                $('#stringCalc').focus();
            }

            function exemplo(ex) {
                switch (ex) {
                    case 1:
                        $('#stringCalc').val('50 + 13 / (25 - 5) - fib(6)');
                        break;
                    case 2:
                        $('#stringCalc').val('(25 * (13 + 2)) - fat(5)');
                        break;
                    case 3:
                        $('#stringCalc').val('sqrt(625) - (-70) / 3 * 5 + 4');
                        break;
                    case 4:
                        $('#stringCalc').val('922 - fat(6) * (3 - 1) + fib(12) + 75 / sqrt(25)');
                        break;
                }
                $('#submit').click();
            }
</script>
</html>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cancellation Invoice</title>
    <style>
        * {
            font-family: 'NotoSerif', serif;
        }

        body {
            margin: 0;
            padding: 0;
        }

        @page {
            margin: 0;

            @top-left {
                content: element(header);
            }

            @bottom-center {
                content: 'Page ' counter(page) ' of ' counter(pages);
            }
        }

        .header {
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            text-align: center;
            background-color: rgb(1,21,104);
            border-bottom: 1px solid #ccc;
        }

        main {
            margin: 50px 50px 50px;
            padding: 20px;
        }

        h2 {
            text-align: center;
        }

        address {
            margin-top: 30pt;
        }

        table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 20px;
        }

        th, td {
            padding: 12px;
            text-align: left;
        }

        th {
            border-bottom: 1px solid black;
        }

        .totals {
            margin-top: 20px;
        }
    </style>
</head>
<body>
<div class="header" style="position: running(header);">
    <img src="images/logo.png" height="50" alt="Ticketline Logo">
</div>
<main>
    <h2>Cancellation Invoice</h2>
    <address>
        Ticketline <br>
        Karlsplatz 13, 1040 Wien <br>
        ATU82739182
    </address>
    <div style="margin-top: 14pt;">
        Purchaser: <br>
        ${order.user.firstName} ${order.user.lastName}<br>
        ${order.user.location.address}, ${order.user.location.postalCode} ${order.user.location.city},
        ${order.user.location.country}
    </div>
    <div>
        Invoice No.: ${invoiceNumber} <br>
        Date: ${orderDate}
    </div>
    <table>
        <thead>
        <tr>
            <th>Count</th>
            <th>Description</th>
            <th>Total Price</th>
            <th>VAT</th>
        </tr>
        </thead>
        <tbody>
        <#list tickets as ticket>
            <#if ticket.ticketCategory == "SEATING">
                <tr>
                    <td>1</td>
                    <td>Row ${ticket.rowNumber} Seat ${ticket.seatNumber}</td>
                    <td>${-event.seatPrice!'-'} €</td>
                    <td>20%</td>
                </tr>
            </#if>
        </#list>
        <#if standingTicketsCount gt 0>
            <tr>
                <td>${standingTicketsCount}</td>
                <td>Standing tickets</td>
                <td>${(standingTicketsCount * -event.standingPrice)!'-'} €</td>
                <td>20%</td>
            </tr>
        </#if>
        </tbody>
    </table>
    <div class="totals">
        Total before tax: ${totalPrice / 1.2} €<br>
        VAT: ${totalPrice - totalPrice / 1.2} €<br>
        Total after tax: ${totalPrice} €
    </div>
</main>
</body>
</html>
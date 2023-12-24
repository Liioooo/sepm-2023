<html lang="en">
<head>
    <title>Document</title>
    <style>
        * {
            font-family: 'NotoSerif', serif;
        }

        @page {
            margin: 50px;

            @top-left {
                content: element(header);
            }

            @bottom-center {
                content: 'Page ' counter(page) ' of ' counter(pages);
            }
        }

        .table {
            border-collapse: collapse;
            border-spacing: 0;
            margin: 2rem 0;
            width: 100%;
        }

        .table th, .table td {
            padding: 0.5em;
        }

        .table th {
            border-bottom: 1px solid black;
        }
    </style>
</head>
<body>
<div class="header" style="position: running(header);">
    <img src="images/logo.png" height="50" alt="Ticketline Logo">
</div>
<main>
    <h1>Invoice</h1>
    <div>
        Ticketline <br>
        Karlsplatz 13, 1040 Wien <br>
        ATU82739182
    </div>
    <div style="margin-top: 14pt;">
        Purchaser <br>
        ${order.user.firstName} ${order.user.lastName}<br>
        ${order.user.location.address}, ${order.user.location.postalCode} ${order.user.location.city},
        ${order.user.location.country}
    </div>
    <div>
        Invoice No.:${order.id} <br>
        Date: ${orderDate}
    </div>
    <table class="table">
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
                    <td>${event.seatPrice!'-'} €</td>
                    <td>20%</td>
                </tr>
            </#if>
        </#list>
        <#if standingTicketsCount gt 0>
            <tr>
                <td>${standingTicketsCount}</td>
                <td>Standing tickets</td>
                <td>${(standingTicketsCount * event.standingPrice)!'-'} €</td>
                <td>20%</td>
            </tr>
        </#if>
        </tbody>
    </table>
    <div>
        Total before tax: ${totalPrice / 1.2} €<br>
        VAT: ${totalPrice - totalPrice / 1.2} €<br>
        Total after tax: ${totalPrice} €
    </div>
</main>
</body>
</html>
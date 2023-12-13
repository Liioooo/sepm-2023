<html lang="en">
<head>
    <title>Document</title>
    <style>
        * {
            font-family: 'NotoSerif', serif;
        }
    </style>
</head>
<body>
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
    <table style="border-collapse: separate; border-spacing: 13pt;">
        <thead>
        <th>Count</th>
        <th>Description</th>
        <th>Total Price</th>
        <th>VAT</th>
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
<html lang="en">
<head>
    <title>Document</title>
    <style>

    </style>
</head>
<body>
<main>
    <h1>Rechnung</h1>
    <div>
        Ticketline <br>
        Karlsplatz 13, 1040 Wien
    </div>
    <div style="margin-top: 14pt;">
        Leistungsempfänger <br>
        ${order.user.firstName} ${order.user.lastName}<br>
        ${order.user.location.address}, ${order.user.location.postalCode} ${order.user.location.city},
        ${order.user.location.country}
    </div>
</main>
</body>
</html>
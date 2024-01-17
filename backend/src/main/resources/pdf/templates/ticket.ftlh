<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ticket</title>
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
            background-color: rgb(1, 21, 104);
            border-bottom: 1px solid #ccc;
        }

        main {
            margin: 50px 50px 50px;
            padding: 20px;
        }

        h2 {
            margin-top: 20px;
        }

        .footer {
            position: fixed;
            bottom: 0;
            left: 0;
            right: 0;
            background-color: rgb(1, 21, 104);
            height: 30px;
        }

        .striped-border {
            border: 3px dashed;
            border-bottom: none;
        }
    </style>
</head>
<body>
<div class="header" style="position: running(header);">
    <img src="images/logo.png" height="50" alt="Ticketline Logo">
</div>
<main>
    <h2>ticketline</h2>
    <p>
        ${order.user.firstName} ${order.user.lastName}<br>
        Date of Order: ${orderDate}
    </p>
    <hr class="striped-border">
    <div style="background-color: rgb(1, 21, 104)">
        <h2 style="margin-left: 10px; color: white">Ticket</h2>
    </div>

    <div style="width: 100%">
        <div style="display: inline-block; width: 30%">
            <p>
                ${event.hall.location.title}<br>
                ${event.hall.location.address}<br>
                ${event.hall.location.postalCode} ${event.hall.location.city}
            </p>
            <p>Category: ${category}<br>
                Row:<br>
                Seat:</p>

        </div>
        <div style="text-align: center; display: inline-block; width: 40%">
            <h3>${artist}</h3>
            <p>${event.title}</p>
            <p>${eventDate}</p>
        </div>
        <div style="text-align: center; display: inline-block; width: 20%">
            <div style="border-color: black; height: 150px; width: 150px">
                <img src=${qrcodePath} height="150" alt="ticket qrcode">
            </div>
            <h3>€ ${price}</h3>
        </div>
    </div>

    <hr class="striped-border">

    <div style="width: 100%">
        <h3>Usage Instructions</h3>
        <p>To ensure entry without difficulty, please consider the following usage instructions</p>
        <ul>
            <li>Print the ticket or have it ready on your mobile device.</li>
            <li>The ticket is valid for a single entry and will be validated with the first check in (unless otherwise defined).</li>
            <li>Only tickets in good print quality can be read by the scanner at the entrance.</li>
            <li>Please protect the ticket from moisture and contamination.</li>
        </ul>
    </div>


</main>
<div class="footer"></div>
</body>
</html>
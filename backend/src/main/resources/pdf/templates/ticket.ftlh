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
        <div style="text-align: center; display: inline-block; width: 50%">
            <h3>${artist}</h3>
            <p>${event.title}</p>
            <p>${eventDate}</p>
        </div>
        <div style="text-align: center; display: inline-block; width: 20%">
            <div style="background-color: black; height: 100px; width: 100px"></div>
            <h3>€ ${price}</h3>
        </div>
    </div>

    <hr class="striped-border">

    <div style="width: 100%">
        <div style="width: 50%; display: inline-block">
            <h3>Usage Instructions</h3>
            <p>To ensure entry without difficulty, please consider the following usage instructions</p>
            <ul>
                <li>Print the ticket or have it ready on your mobile device.</li>
                <li>The ticket is valid for a single entry and will be validated with the first check in (unless otherwise defined).</li>
                <li>Only tickets in good print quality can be read by the scanner at the entrance.</li>
                <li>Please protect the ticket from moisture and contamination.</li>
                <li>Fold the ticket according to the instructions.</li>
            </ul>
        </div>
        <div style="width: 50%; display: inline-block">
            <h3>Folding Instructions</h3>
            <p>For the entry control, this ticket needs to be folded. Please follow these steps:</p>
            <ol>
                <li>FOLD THE TICKET IN HALF <br> along the dashed line (Fold Mark 1) so that the printed side is on the outside.</li>
                <li>FOLD THE TICKET AGAIN <br> along the second dashed line (Fold Mark 2) so that the folding instructions disappear.</li>
                <li>DONE! <br> Now you have the original ticket on one side and the booking data on the other side. You are ready for check-in.</li>
            </ol>
        </div>
    </div>


</main>
<div class="footer"></div>
</body>
</html>
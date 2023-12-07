import {Component, OnInit, Input} from '@angular/core';
import Chart from 'chart.js/auto';
import {plugins} from "chart.js";

@Component({
  selector: 'app-piechart',
  templateUrl: './piechart.component.html',
  styleUrls: ['./piechart.component.scss']
})
export class PiechartComponent implements OnInit {
  @Input() searchMonth: string = "";
  @Input() searchCategory: string = "";
  constructor() { }

  ngOnInit(): void {
    this.createChart();
  }
  public chart: any;

  createChart() {
    this.chart = new Chart("MyChart", {
      type: 'pie',
      // type: 'doughnut',
      data: {
        labels: [
          //da gehören die Event names hin
          'Wheat',
          'Maize',
          'Rice',
          'Sugarcane',
          'Cotton'
        ],
        datasets: [{
          label: 'Top 10 '+ this.searchCategory + ' in '+ this.searchMonth,
          data: [9168.2, 1417.8, 3335.1, 1165.0, 2078.9],
          backgroundColor: [
            'rgb(255, 99, 132)',
            'rgb(54, 162, 235)',
            'rgb(255, 205, 86)',
            'rgb(75, 192, 192)',
            'rgb(153, 102, 255)',
            'rgb(150, 12, 200)',
            'rgb(100, 120, 100)',
            'rgb(50, 120, 100)',
            'rgb(0, 0, 100)',
            'rgb(220, 20, 80)'
          ],
          hoverOffset: 4
        }]
      },
      options: {
        aspectRatio: 2.5,
        plugins: {
          title: {
            display: true,
            text: 'Top 10 '+ this.searchCategory + ' in '+ this.searchMonth,
            font: {
              size: 24,
              weight: 'bold',
              family: "'Helvetica Neue', 'Helvetica', 'Arial', sans-serif"
            },
            padding: {
              top: 10,
              bottom: 30
            }
          },
          legend: {
            display: true,
            labels: {
              font: {
                size: 14,
                family: "'Helvetica Neue', 'Helvetica', 'Arial', sans-serif"
              }
            }
          }
        }
      }
    });
  }}

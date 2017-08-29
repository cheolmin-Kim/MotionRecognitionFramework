jQuery(function ($) {
    function deferRender (proceed) {
        var series = this,
            $renderTo = $(this.chart.container.parentNode);

            if ($renderTo.is(':appeared') || !series.options.animation) {
                proceed.call(series);
            }

            $renderTo.each(function() {
                $(this).appear();
                $(this).on('appear', function() {
                    proceed.call(series);
                    $(this).unbind();
                });
            });
    }

    if ($(document).width() >= 768) {
        // Only use jQuery Appear Plugin on Desktop / Tablets.
        var render = Highcharts.Series.prototype.render;
        Highcharts.wrap(Highcharts.Series.prototype, 'render', deferRender);

        $(window).bind('resize', function () {

            // Reset to prevent loop from window.resize => chart.reflow
            Highcharts.Series.prototype.render = render;
        })
    }


    var animDuration = 1517;
    $("#highcharts-demo-box .demo-frame-container").highcharts({
        colors: ["#B7FFB5", "#fff", "#f45b5b", "#7798BF", "#aaeeee", "#ff0066", "#eeaaee",
            "#55BF3B", "#DF5353", "#7798BF", "#aaeeee"
        ],
        credits: {
            enabled: false
        },
        chart: {
            spacing: 20,
            height: 360,
            backgroundColor: '#61BC7B',
            style: {
                fontFamily: 'Roboto, sans-serif'
            }
        },
        title: {
            text: 'Tokyo Climate',
            style: {
                color: 'white'
            }
        },
        legend: {
            enabled: false
        },
        plotOptions: {
            series: {
                borderColor: 'none'
            }
        },
        xAxis: [{
            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
            tickColor: '#A0D9B0',
            tickWidth: 0,
            lineColor: '#A0D9B0',
            lineWidth: 2,

            labels: {
                style: {
                    color: '#FFF',
                    fontSize: '1em'
                }
            }
        }],
        yAxis: [{ // Primary yAxis
            labels: {
                formatter: function() {
                    return this.value + '째C';
                },
                align: 'left',
                x: 0,
                y: -2,
                style: {
                    color: '#FFF'
                }
            },
            showFirstLabel: false,
            showLastLabel: false,
            title: {
                text: 'Temperature',
                style: {
                    color: '#FFF',
                    fontSize: '1.2em'
                }
            }
        }, { // Secondary yAxis
            gridLineColor: '#A0D9B0',
            title: {
                text: 'Rainfall',
                style: {
                    color: '#FFF',
                    fontSize: '1.2em'
                }
            },
            labels: {
                align: 'right',
                x: 0,
                y: -2,
                formatter: function() {
                    return this.value + ' mm';
                },
                style: {
                    color: '#FFF'
                }
            },
            showFirstLabel: false,
            showLastLabel: false,
            opposite: true
        }],
        tooltip: {
            formatter: function() {
                if (this.series.name == 'Sunshine') {
                    return '<b>' + this.point.name + ':</b> ' + this.y;
                } else {
                    return '' + this.x + ': ' + this.y + (this.series.name == 'Rainfall' ? ' mm' : '째C');
                }
            }
        },
        series: [{
            name: 'Rainfall',
            type: 'column',
            yAxis: 1,
            data: [49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4],
            animation: {
                duration: animDuration
            }

        }, {
            name: 'Temperature',
            type: 'spline',
            data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6],
            animation: {
                duration: animDuration
            }
        }, {
            name: 'Sunshine',
            type: 'pie',
            data: [{
                y: 2020,
                name: 'Sunshine hours',
                sliced: true,
                color: '#FFD664',
                dataLabels: {
                    style: {
                        color: 'white',
                        textShadow: false,
                        fontSize: '1em'
                    }
                }
            }, {
                y: 6740,
                name: 'Non sunshine hours (including night)',
                dataLabels: {
                    enabled: false
                },
                color: '#59936A'
            }],
            center: [70, 45],
            size: 80,
            animation: {
                duration: animDuration
            }
        }],
        navigation: {
            buttonOptions: {
                symbolStroke: 'rgba(255, 255, 255, 0.8)',
                theme: {
                    fill: 'rgba(255, 255, 255, 0.2)',
                    states: {
                        hover: {
                            fill: 'rgba(255, 255, 255, 0.3)',
                            stroke: 'transparent'
                        },
                        select: {
                            fill: 'rgba(255, 255, 255, 0.3)',
                            stroke: 'transparent'
                        }
                    }
                }
            }
        }
    });


    $.getJSON('https://www.highcharts.com/samples/data/jsonp.php?filename=usdeur.json&callback=?', function(data) {
    var animDuration = 1517;
    var textColor = '#D2E9FF';
    $("#highstock-demo-box").find(".demo-frame-container").highcharts('StockChart', {
        colors: ["#FFF", "#FFF", "#f45b5b", "#7798BF", "#aaeeee", "#ff0066", "#eeaaee",
            "#55BF3B", "#DF5353", "#7798BF", "#aaeeee"
        ],
        credits: {
            enabled: false
        },
        chart: {
            spacing: 20,
            height: 360,
            backgroundColor: '#508CC8',
            style: {
                fontFamily: 'Roboto, sans-serif'
            }
        },

        title: {
            text: 'USD to EUR Exchange Rate Over Time',
            style: {
                color: textColor
            }
        },

        xAxis: {
            maxZoom: 14 * 24 * 3600000, // fourteen days
            tickColor: '#80A4C9',
            tickWidth: 1,
            lineColor: '#80A4C9',
            lineWidth: 2,
            labels: {
                style: {
                    color: textColor
                }
            }
        },
        yAxis: [{ // Primary yAxis
            gridLineColor: '#80A4C9',
            labels: {
                style: {
                    color: textColor
                }
            }
        }],
        series: [{
            name: 'USD to EUR',
            type: 'area',
            data: data,
            tooltip: {
                yDecimals: 4
            },
            fillColor: {
                linearGradient: {
                    x1: 0,
                    y1: 0,
                    x2: 0,
                    y2: 1
                },
                stops: [
                    [0, Highcharts.getOptions().colors[0]],
                    [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                ]
            },
            animation: {
                duration: animDuration
            },
            threshold: null
        }],

        navigation: {
            buttonOptions: {
                symbolStroke: 'rgba(255, 255, 255, 0.8)',
                theme: {
                    fill: 'rgba(255, 255, 255, 0.1)',
                    states: {
                        hover: {
                            fill: 'rgba(255, 255, 255, 0.2)'
                        },
                        select: {
                            fill: 'rgba(255, 255, 255, 0.2)'
                        }
                    }
                }
            }
        },
        scrollbar: {
            barBackgroundColor: '#29467D',
            barBorderColor: '#29467D',
            buttonArrowColor: '#CCC',
            buttonBackgroundColor: '#38618B',
            buttonBorderColor: '#38618B',
            rifleColor: '#FFF',
            trackBackgroundColor: '#4577AA',
            trackBorderColor: '#4577AA'
        },

        navigator: {
            handles: {
                backgroundColor: '#A5B4CE',
                borderColor: '#29467D'
            },
            outlineColor: '#29467D',
            maskFill: 'rgba(21, 44, 87, 0.15)',
            series: {
                color: 'transparent',
                lineColor: '#fff'
            },
            xAxis: {
                gridLineColor: '#80A4C9',
                labels: {
                    style: {
                        color: textColor
                    }
                }
            }
        },
        // scroll charts
        rangeSelector: {
            buttonTheme: {
                fill: '#63A0DD',
                stroke: '#000000',
                style: {
                    color: textColor
                },
                states: {
                    hover: {
                        fill: '#8DBFF2',
                        stroke: '#000000',
                        style: {
                            color: 'white'
                        }
                    },
                    select: {
                        fill: '#235F9B',
                        stroke: '#235F9B',
                        style: {
                            color: 'white'
                        }
                    }
                }
            },
            inputBoxBorderColor: '#CDDAEA',
            inputStyle: {
                backgroundColor: '#333',
                color: textColor
            },
            labelStyle: {
                color: textColor
            },
            selected: 4,
            inputEnabled: false
        }
    });
    });


    $.getJSON('https://www.highcharts.com/samples/data/jsonp.php?filename=world-population-density.json&callback=?', function(data) {
        $("#highmaps-demo-box").find(".demo-frame-container").highcharts('Map', {
            colors: ["#67B86D", "#19E1CF", "#f45b5b", "#7798BF", "#aaeeee", "#ff0066", "#eeaaee",
                "#55BF3B", "#DF5353", "#7798BF", "#aaeeee"
            ],
            credits: {
                enabled: true,
                style: {
                    color: 'rgba(255, 255, 255, 0.6)'
                }
            },
            chart: {
                spacing: 20,
                height: 360,
                backgroundColor: '#F49952',
                style: {
                    fontFamily: 'Roboto, sans-serif'
                }
            },
            mapNavigation: {
                buttonOptions: {
                    verticalAlign: 'bottom',
                    theme: {
                        fill: 'rgba(255, 255, 255, 0.2)',
                        stroke: 'rgba(255, 255, 255, 0.7)',
                        style: {
                            color: 'white'
                        },
                        states: {
                            hover: {
                                fill: 'rgba(255, 255, 255, 0.4)',
                                stroke: 'rgba(255, 255, 255, 0.7)'
                            },
                            select: {
                                fill: 'rgba(255, 255, 255, 0.4)',
                                stroke: 'rgba(255, 255, 255, 0.7)'
                            }
                        }
                    }
                },
                enabled: true,
                enableMouseWheelZoom: false
            },
            title: {
                text: 'World Population Density',
                style: {
                    color: 'rgba(255, 255, 255, 0.9)',
                }
            },
            xAxis: {
                minRange: 200
            },
            colorAxis: {
                min: 1,
                max: 1000,
                minColor: '#F0D2BC',
                maxColor: '#FFC92E',
                type: 'logarithmic'
            },
            legend: {
                enabled: false
            },

            series: [{
                data: data,
                borderColor: '#BA752E',
                mapData: Highcharts.maps['custom/world'],
                joinBy: ['iso-a2', 'code'],
                name: 'Population density',
                states: {
                    hover: {
                        color: '#7280bd',
                        borderColor: '#BEC9F8',
                        borderWidth: 2
                    }
                },
                tooltip: {
                    valueSuffix: '/km짼'
                },
                animation: {
                    duration: animDuration
                }
            }],
            navigation: {
                buttonOptions: {
                    symbolStroke: 'rgba(255, 255, 255, 0.8)',
                    theme: {
                        fill: 'rgba(255, 255, 255, 0.2)',
                        states: {
                            hover: {
                                fill: 'rgba(255, 255, 255, 0.4)',
                                stroke: 'transparent'
                            },
                            select: {
                                fill: 'rgba(255, 255, 255, 0.4)',
                                stroke: 'transparent',
                            }
                        }
                    }
                }
            }
        });
    });
});
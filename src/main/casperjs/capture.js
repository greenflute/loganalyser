// ============================================================================
// CasperJS Web Page Capture
// possible command line parameters:
// --debug=true --proxy=ip:port --proxy-type=socks5

// ============================================================================
// system module and objects
"use strict";
var casper      = require('casper').create({
        verbose: false,
        logLevel: 'debug',
        viewportSize: {width: 1440, height: 900}
    });
var fs          = require('fs');
var utils       = require('utils');
var clientutils = require('clientutils').create();
var colorizer   = require('colorizer').create('Colorizer');

// ============================================================================
// utility functions
//function prefix(){ return Date.now(); /*.toLocaleFormat('%Y%m%d_$M$S_');*/}
function prefix() {
    var now = new Date(), month = now.getMonth() + 1, day = now.getDate(), hour = now.getHours(), minute = now.getMinutes();

    return now.getFullYear() + (month < 10 ? '0' : '') + month + (day < 10 ? '0' : '') + day + '_' + (hour < 10 ? '0' : '') + hour + (minute < 10 ? '0' : '') + minute;
}
function error(message) { casper.echo(prefix() + ': ' + message, 'WARNING'); }
function warn(message)  { casper.echo(prefix() + ': ' + message, 'COMMENT'); }
function info(message)  { casper.echo(prefix() + ': ' + message, 'INFO'); }

// ============================================================================
// configuration
//casper.userAgent('Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.1 Safari/537.36');

// ============================================================================
// check parameter
if (!casper.cli.has(0)) {
    error('Must at least given one url or file.');
    casper.exit();
}

// ============================================================================
// open
var url = casper.cli.get(0);

casper.start(url, function() {
	info("Processing " + url);
	casper.waitForSelector('#clip', function () {

        info('Diagram ready ...');
        this.capture('vibration '+url+'.png', {top: 0, left: 0, width: 1440, height: 1020});
        info('Diagram saved!');

    }, function () {

        error('Operation failed for ' + url);
        // casper.exit();            
    }, 500000);
});

casper.run();
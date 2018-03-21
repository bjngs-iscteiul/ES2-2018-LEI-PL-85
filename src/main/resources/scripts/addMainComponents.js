$('#topHeader').append($('<table cellspacing="30">\n' +
    '        <th>\n' +
    '            <a class="linkHeader" href="index.html">HOME</a>\n' +
    '        </th>\n' +
    '        <th>\n' +
    '            <a class="linkHeader" href="problemView.html">PROBLEM</a>\n' +
    '        </th>\n' +
    '        <th>\n' +
    '            <a class="linkHeader" href="faqView.html">FAQ</a>\n' +
    '        </th>\n' +
    '        <th>\n' +
    '            <a class="linkHeader" href="supportView.html">SUPPORT</a>\n' +
    '        </th>\n' +
    '    </table>'));


window.onscroll = function() {myFunction()};

var header = document.getElementById("topHeader");
var sticky = header.offsetTop;

function myFunction() {
    if (window.pageYOffset >= sticky) {
        header.classList.add("sticky");
    } else {
        header.classList.remove("sticky");
    }
}


$('#bottomFooter').append($());

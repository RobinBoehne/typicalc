const subscripted = [...'\u2080\u2081\u2082\u2083\u2084\u2085\u2086\u2087\u2088\u2089'];

window.addTypeInputListener = (str) => {
	var el = document.getElementsByClassName(str);
	if (el) {
		Array.from(el).forEach(function(element) {
			element.addEventListener('keyup', e => {
				var area = e.target.shadowRoot.querySelector('input');
				listener(area);
			});
		});
		Array.from(el).forEach(function(element) {
			element.addEventListener('focus', e => {
				var area = e.target.shadowRoot.querySelector('input');
				listener(area);
			});
		});
	}
}

function listener(area) {
	var value = parseBack(area.value);
	var start = area.selectionStart;
	var end = area.selectionEnd;
	if (value.startsWith('t') && isNumeric(value.substr(1, value.length - 1))) {
		area.value = value.replace('t', '\u03C4');
		area.value = area.value.replace(/[0123456789]/g, toUnicode);
	} else {
		area.value = value;
	}
	area.selectionStart = start;
	area.selectionEnd = end;
}


function toUnicode(number) {
    return subscripted[number];
}

function toNumber(unicode) {
	var result = subscripted.indexOf(unicode);
    if(result > -1) { return result; }
    else { return unicode; }
}

function parseBack(value) {
	value = value.replace('\u03C4', 't');
	return value.replace(/./g, toNumber);
}

function isNumeric(value) {
	return /^\d+$/.test(value);
}
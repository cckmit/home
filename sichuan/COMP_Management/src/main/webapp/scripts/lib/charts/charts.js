(function() {
	var I = document,
		aD = window,
		W = Math,
		u = W.round,
		R = W.floor,
		bc = W.ceil,
		aQ = W.max,
		ad = W.min,
		ba = W.abs,
		aU = W.cos,
		C = W.sin,
		o = W.PI,
		bb = o * 2 / 360,
		e = navigator.userAgent,
		a2 = /msie/i.test(e) && !aD.opera,
		aX = I.documentMode === 8,
		az = /AppleWebKit/.test(e),
		f = /Firefox/.test(e),
		ae = "http://www.w3.org/2000/svg",
		c = !! I.createElementNS && !! I.createElementNS(ae, "svg").createSVGRect,
		aM = f && parseInt(e.split("Firefox/")[1], 10) < 4,
		aG, aZ = I.documentElement.ontouchstart !== undefined,
		aq = {},
		Q = 0,
		N = 1,
		D, aK, aB, n, k, aA, an = "div",
		a0 = "absolute",
		m = "relative",
		at = "hidden",
		z = "charts-",
		al = "visible",
		aj = "px",
		ah = "none",
		au = "M",
		av = "L",
		aF = "rgba(192,192,192," + (c ? 0.000001 : 0.002) + ")",
		Y = "",
		S = "hover",
		v = "select",
		aW, J, aR, ai, a5, aS, ak, aH, G, aT, H, y, a6 = {};

	function aw(M, L) {
		var be;
		if (!M) {
			M = {}
		}
		for (be in L) {
			M[be] = L[be]
		}
		return M
	}
	function p(L, M) {
		return parseInt(L, M || 10)
	}
	function a1(L) {
		return typeof L === "string"
	}
	function F(L) {
		return typeof L === "object"
	}
	function d(L) {
		return Object.prototype.toString.call(L) === "[object Array]"
	}
	function V(L) {
		return typeof L === "number"
	}
	function aY(L) {
		return W.log(L) / W.LN10
	}
	function aJ(L) {
		return W.pow(10, L)
	}
	function aO(L, be) {
		var M = L.length;
		while (M--) {
			if (L[M] === be) {
				L.splice(M, 1);
				break
			}
		}
	}
	function j(L) {
		return L !== aA && L !== null
	}
	function ac(be, bh, bg) {
		var M, bf = "setAttribute",
			L;
		if (a1(bh)) {
			if (j(bg)) {
				be[bf](bh, bg)
			} else {
				if (be && be.getAttribute) {
					L = be.getAttribute(bh)
				}
			}
		} else {
			if (j(bh) && F(bh)) {
				for (M in bh) {
					be[bf](M, bh[M])
				}
			}
		}
		return L
	}
	function af(L) {
		return d(L) ? L : [L]
	}
	function b() {
		var M = arguments,
			be, L, bf = M.length;
		for (be = 0; be < bf; be++) {
			L = M[be];
			if (typeof L !== "undefined" && L !== null) {
				return L
			}
		}
	}
	function ax(L, M) {
		if (a2) {
			if (M && M.opacity !== aA) {
				M.filter = "alpha(opacity=" + (M.opacity * 100) + ")"
			}
		}
		aw(L.style, M)
	}
	function ar(L, bh, bg, bf, be) {
		var M = I.createElement(L);
		if (bh) {
			aw(M, bh)
		}
		if (be) {
			ax(M, {
				padding: 0,
				border: ah,
				margin: 0
			})
		}
		if (bg) {
			ax(M, bg)
		}
		if (bf) {
			bf.appendChild(M)
		}
		return M
	}
	function aP(be, L) {
		var M = function() {};
		M.prototype = new be();
		aw(M.prototype, L);
		return M
	}
	function i(bf, be, bl, bk) {
		var L = aK.lang,
			M = bf,
			bj = isNaN(be = ba(be)) ? 2 : be,
			bi = bl === undefined ? L.decimalPoint : bl,
			bm = bk === undefined ? L.thousandsSep : bk,
			bn = M < 0 ? "-" : "",
			bh = String(p(M = ba(+M || 0).toFixed(bj))),
			bg = bh.length > 3 ? bh.length % 3 : 0;
		return bn + (bg ? bh.substr(0, bg) + bm : "") + bh.substr(bg).replace(/(\d{3})(?=\d)/g, "$1" + bm) + (bj ? bi + ba(M - bh).toFixed(bj).slice(2) : "")
	}
	aB = function(bm, bi, bg) {
		function M(bq) {
			return bq.toString().replace(/^([0-9])$/, "0$1")
		}
		if (!j(bi) || isNaN(bi)) {
			return "Invalid date"
		}
		bm = b(bm, "%Y-%m-%d %H:%M:%S");
		var be = new Date(bi * N),
			bo, bl = be[aR](),
			bj = be[ai](),
			bn = be[a5](),
			bh = be[aS](),
			bp = be[ak](),
			L = aK.lang,
			bk = L.weekdays,
			bf = {
				a: bk[bj].substr(0, 3),
				A: bk[bj],
				d: M(bn),
				e: bn,
				b: L.shortMonths[bh],
				B: L.months[bh],
				m: M(bh + 1),
				y: bp.toString().substr(2, 2),
				Y: bp,
				H: M(bl),
				I: M((bl % 12) || 12),
				l: (bl % 12) || 12,
				M: M(be[J]()),
				p: bl < 12 ? "AM" : "PM",
				P: bl < 12 ? "am" : "pm",
				S: M(be.getSeconds())
			};
		for (bo in bf) {
			bm = bm.replace("%" + bo, bf[bo])
		}
		return bg ? bm.substr(0, 1).toUpperCase() + bm.substr(1) : bm
	};

	function s(L) {
		var M = {
			left: L.offsetLeft,
			top: L.offsetTop
		};
		L = L.offsetParent;
		while (L) {
			M.left += L.offsetLeft;
			M.top += L.offsetTop;
			if (L !== I.body && L !== I.documentElement) {
				M.left -= L.scrollLeft;
				M.top -= L.scrollTop
			}
			L = L.offsetParent
		}
		return M
	}
	function A() {
		this.color = 0;
		this.symbol = 0
	}
	A.prototype = {
		wrapColor: function(L) {
			if (this.color >= L) {
				this.color = 0
			}
		},
		wrapSymbol: function(L) {
			if (this.symbol >= L) {
				this.symbol = 0
			}
		}
	};

	function X(M, bh, be, bf, bn, bl, bk) {
		var L = bk.x,
			bm = bk.y,
			bj = L - M + be - 25,
			bi = bm - bh + bf + 10,
			bg;
		if (bj < 7) {
			bj = be + L + 15
		}
		if ((bj + M) > (be + bn)) {
			bj -= (bj + M) - (be + bn);
			bi -= bh;
			bg = true
		}
		if (bi < 5) {
			bi = 5;
			if (bg && bm >= bi && bm <= (bi + bh)) {
				bi = bm + bh - 5
			}
		} else {
			if (bi + bh > bf + bl) {
				bi = bf + bl - bh - 5
			}
		}
		return {
			x: bj,
			y: bi
		}
	}
	function K(M, L) {
		var bf = M.length,
			be;
		for (be = 0; be < bf; be++) {
			M[be].ss_i = be
		}
		M.sort(function(bi, bg) {
			var bh = L(bi, bg);
			return bh === 0 ? bi.ss_i - bg.ss_i : bh
		});
		for (be = 0; be < bf; be++) {
			delete M[be].ss_i
		}
	}
	function a9(L) {
		var M;
		for (M in L) {
			if (L[M] && L[M].destroy) {
				L[M].destroy()
			}
			delete L[M]
		}
	}
	k = {
		init: function(bg, bl, bm) {
			bl = bl || "";
			var M = bg.shift,
				be = bl.indexOf("C") > -1,
				bf = be ? 7 : 3,
				bj, bn, bi, L = bl.split(" "),
				bh = [].concat(bm),
				bp, bk, bo = function(bq) {
					bi = bq.length;
					while (bi--) {
						if (bq[bi] === au) {
							bq.splice(bi + 1, 0, bq[bi + 1], bq[bi + 2], bq[bi + 1], bq[bi + 2])
						}
					}
				};
			if (be) {
				bo(L);
				bo(bh)
			}
			if (bg.isArea) {
				bp = L.splice(L.length - 6, 6);
				bk = bh.splice(bh.length - 6, 6)
			}
			if (M) {
				bh = [].concat(bh).splice(0, bf).concat(bh);
				bg.shift = false
			}
			if (L.length) {
				bj = bh.length;
				while (L.length < bj) {
					bn = [].concat(L).splice(L.length - bf, bf);
					if (be) {
						bn[bf - 6] = bn[bf - 2];
						bn[bf - 5] = bn[bf - 1]
					}
					L = L.concat(bn)
				}
			}
			if (bp) {
				L = L.concat(bp);
				bh = bh.concat(bk)
			}
			return [L, bh]
		},
		step: function(bi, M, bh, L) {
			var bf = [],
				bg = bi.length,
				be;
			if (bh === 1) {
				bf = L
			} else {
				if (bg === M.length && bh < 1) {
					while (bg--) {
						be = parseFloat(bi[bg]);
						bf[bg] = isNaN(be) ? bi[bg] : bh * (parseFloat(M[bg] - be)) + be
					}
				} else {
					bf = M
				}
			}
			return bf
		}
	};

	function ay(M, L) {
		n = b(M, L.animation)
	}
	if (aD.jQuery) {
		var P = jQuery;
		each = function(M, bf) {
			var be = 0,
				L = M.length;
			for (; be < L; be++) {
				if (bf.call(M[be], M[be], be, M) === false) {
					return be
				}
			}
		};
		grep = P.grep;
		map = function(M, bg) {
			var bf = [],
				be = 0,
				L = M.length;
			for (; be < L; be++) {
				bf[be] = bg.call(M[be], M[be], be, M)
			}
			return bf
		};
		merge = function() {
			var L = arguments;
			return P.extend(true, null, L[0], L[1], L[2], L[3])
		};
		addEvent = function(M, be, L) {
			P(M).bind(be, L)
		};
		removeEvent = function(be, L, M) {
			var bf = I.removeEventListener ? "removeEventListener" : "detachEvent";
			if (I[bf] && !be[bf]) {
				be[bf] = function() {}
			}
			P(be).unbind(L, M)
		};
		fireEvent = function(bf, be, M, L) {
			var bh = P.Event(be),
				bg = "detached" + be;
			aw(bh, M);
			if (bf[be]) {
				bf[bg] = bf[be];
				bf[be] = null
			}
			P(bf).trigger(bh);
			if (bf[bg]) {
				bf[be] = bf[bg];
				bf[bg] = null
			}
			if (L && !bh.isDefaultPrevented()) {
				L(bh)
			}
		};
		animate = function(be, bf, L) {
			var M = P(be);
			if (bf.d) {
				be.toD = bf.d;
				bf.d = 1
			}
			M.stop();
			M.animate(bf, L)
		};
		stop = function(L) {
			P(L).stop()
		};
		P.extend(P.easing, {
			easeOutQuad: function(M, be, L, bg, bf) {
				return -bg * (be /= bf) * (be - 2) + L
			}
		});
		var aI = jQuery.fx,
			ao = aI.step;
		each(["cur", "_default", "width", "height"], function(M, L) {
			var bg = L ? ao : aI.prototype,
				bf = bg[M],
				be;
			if (bf) {
				bg[M] = function(bh) {
					bh = L ? bh : this;
					be = bh.elem;
					return be.attr ? be.attr(bh.prop, bh.now) : bf.apply(this, arguments)
				}
			}
		});
		ao.d = function(be) {
			var M = be.elem;
			if (!be.started) {
				var L = k.init(M, M.d, M.toD);
				be.start = L[0];
				be.end = L[1];
				be.started = true
			}
			M.attr("d", k.step(be.start, be.end, be.pos, M.toD))
		}
	}
	function aE() {
		var L = aK.global.useUTC;
		aW = L ? Date.UTC : function(bg, bh, be, M, bf, bi) {
			return new Date(bg, bh, b(be, 1), b(M, 0), b(bf, 0), b(bi, 0)).getTime()
		};
		J = L ? "getUTCMinutes" : "getMinutes";
		aR = L ? "getUTCHours" : "getHours";
		ai = L ? "getUTCDay" : "getDay";
		a5 = L ? "getUTCDate" : "getDate";
		aS = L ? "getUTCMonth" : "getMonth";
		ak = L ? "getUTCFullYear" : "getFullYear";
		aH = L ? "setUTCMinutes" : "setMinutes";
		G = L ? "setUTCHours" : "setHours";
		aT = L ? "setUTCDate" : "setDate";
		H = L ? "setUTCMonth" : "setMonth";
		y = L ? "setUTCFullYear" : "setFullYear"
	}
	function aC(L) {
		aK = merge(aK, L);
		aE();
		return aK
	}
	function E() {
		return aK
	}
	function U(L) {
		if (!D) {
			D = ar(an)
		}
		if (L) {
			D.appendChild(L)
		}
		D.innerHTML = ""
	}
	var aa = {
		enabled: true,
		align: "center",
		x: 0,
		y: 15,
		style: {
			color: "#666",
			fontSize: "11px",
			lineHeight: "14px"
		}
	};
	aK = {
		colors: ["#7CB5EC", "#434348", "#90ED7D", "#F7A35C", "#8085E9", "#F15C80", "#E4D354", "#8085E8", "#8D4653"],
		symbols: ["circle", "diamond", "square", "triangle", "triangle-down"],
		lang: {
			loading: "Loading...",
			months: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"],
			shortMonths: ["Jan", "Feb", "Mar", "Apr", "May", "June", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
			weekdays: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"],
			decimalPoint: ".",
			resetZoom: "Reset zoom",
			resetZoomTitle: "Reset zoom level 1:1",
			thousandsSep: ","
		},
		global: {
			useUTC: true
		},
		chart: {
			borderColor: "#4572A7",
			borderRadius: 5,
			defaultSeriesType: "line",
			ignoreHiddenSeries: true,
			spacingTop: 10,
			spacingRight: 10,
			spacingBottom: 15,
			spacingLeft: 10,
			style: {
				fontFamily: '"Lucida Grande", "Lucida Sans Unicode", Verdana, Arial, Helvetica, sans-serif',
				fontSize: "12px"
			},
			backgroundColor: "#FFFFFF",
			plotBorderColor: "#C0C0C0"
		},
		title: {
			text: "Chart title",
			align: "center",
			y: 15,
			style: {
				color: "#3E576F",
				fontSize: "16px"
			}
		},
		subtitle: {
			text: "",
			align: "center",
			y: 30,
			style: {
				color: "#6D869F"
			}
		},
		plotOptions: {
			line: {
				allowPointSelect: false,
				showCheckbox: false,
				animation: {
					duration: 1000
				},
				events: {},
				lineWidth: 2,
				shadow: true,
				marker: {
					enabled: true,
					lineWidth: 0,
					radius: 4,
					lineColor: "#FFFFFF",
					states: {
						hover: {},
						select: {
							fillColor: "#FFFFFF",
							lineColor: "#000000",
							lineWidth: 2
						}
					}
				},
				point: {
					events: {}
				},
				dataLabels: merge(aa, {
					enabled: false,
					y: -6,
					formatter: function() {
						return this.y
					}
				}),
				showInLegend: true,
				states: {
					hover: {
						marker: {}
					},
					select: {
						marker: {}
					}
				},
				stickyTracking: true
			}
		},
		labels: {
			style: {
				position: a0,
				color: "#3E576F"
			}
		},
		legend: {
			enabled: true,
			align: "center",
			layout: "horizontal",
			labelFormatter: function() {
				return this.name
			},
			borderWidth: 1,
			borderColor: "#909090",
			borderRadius: 5,
			shadow: false,
			style: {
				padding: "5px"
			},
			itemStyle: {
				cursor: "pointer",
				color: "#3E576F"
			},
			itemHoverStyle: {
				cursor: "pointer",
				color: "#000000"
			},
			itemHiddenStyle: {
				color: "#C0C0C0"
			},
			itemCheckboxStyle: {
				position: a0,
				width: "13px",
				height: "13px"
			},
			symbolWidth: 16,
			symbolPadding: 5,
			verticalAlign: "bottom",
			x: 0,
			y: 0
		},
		loading: {
			hideDuration: 100,
			labelStyle: {
				fontWeight: "bold",
				position: m,
				top: "1em"
			},
			showDuration: 100,
			style: {
				position: a0,
				backgroundColor: "white",
				opacity: 0.5,
				textAlign: "center"
			}
		},
		tooltip: {
			enabled: true,
			backgroundColor: "rgba(255, 255, 255, .85)",
			borderWidth: 2,
			borderRadius: 5,
			shadow: true,
			snap: aZ ? 25 : 10,
			style: {
				color: "#333333",
				fontSize: "12px",
				padding: "5px",
				whiteSpace: "nowrap"
			}
		},
		toolbar: {
			itemStyle: {
				color: "#4572A7",
				cursor: "pointer"
			}
		}
	};
	var am = {
		dateTimeLabelFormats: {
			second: "%H:%M:%S",
			minute: "%H:%M",
			hour: "%H:%M",
			day: "%e. %b",
			week: "%e. %b",
			month: "%b '%y",
			year: "%Y"
		},
		endOnTick: false,
		gridLineColor: "#C0C0C0",
		labels: aa,
		lineColor: "#C0D0E0",
		lineWidth: 1,
		max: null,
		min: null,
		minPadding: 0.01,
		maxPadding: 0.01,
		minorGridLineColor: "#E0E0E0",
		minorGridLineWidth: 1,
		minorTickColor: "#A0A0A0",
		minorTickLength: 2,
		minorTickPosition: "outside",
		startOfWeek: 1,
		startOnTick: false,
		tickColor: "#C0D0E0",
		tickLength: 5,
		tickmarkPlacement: "between",
		tickPixelInterval: 100,
		tickPosition: "outside",
		tickWidth: 1,
		title: {
			align: "middle",
			style: {
				color: "#6D869F",
				fontWeight: "bold"
			}
		},
		type: "linear"
	},
		aV = merge(am, {
			endOnTick: true,
			gridLineWidth: 1,
			tickPixelInterval: 72,
			showLastLabel: true,
			labels: {
				align: "right",
				x: -8,
				y: 3
			},
			lineWidth: 0,
			maxPadding: 0.05,
			minPadding: 0.05,
			startOnTick: true,
			tickWidth: 0,
			title: {
				rotation: 270,
				text: "Y-values"
			},
			stackLabels: {
				enabled: false,
				formatter: function() {
					return this.total
				},
				style: aa.style
			}
		}),
		ag = {
			labels: {
				align: "right",
				x: -8,
				y: null
			},
			title: {
				rotation: 270
			}
		},
		B = {
			labels: {
				align: "left",
				x: 8,
				y: null
			},
			title: {
				rotation: 90
			}
		},
		a4 = {
			labels: {
				align: "center",
				x: 0,
				y: 14
			},
			title: {
				rotation: 0
			}
		},
		t = merge(a4, {
			labels: {
				y: -5
			}
		});
	var bd = aK.plotOptions,
		a7 = bd.line;
	bd.spline = merge(a7);
	bd.scatter = merge(a7, {
		lineWidth: 0,
		states: {
			hover: {
				lineWidth: 0
			}
		}
	});
	bd.area = merge(a7, {});
	bd.areaspline = merge(bd.area);
	bd.column = merge(a7, {
		borderColor: "#FFFFFF",
		borderWidth: 1,
		borderRadius: 0,
		groupPadding: 0.2,
		marker: null,
		pointPadding: 0.1,
		minPointLength: 0,
		states: {
			hover: {
				brightness: 0.1,
				shadow: false
			},
			select: {
				color: "#C0C0C0",
				borderColor: "#000000",
				shadow: false
			}
		},
		dataLabels: {
			y: null,
			verticalAlign: null
		}
	});
	bd.bar = merge(bd.column, {
		dataLabels: {
			align: "left",
			x: 5,
			y: 0
		}
	});
	bd.pie = merge(a7, {
		borderColor: "#FFFFFF",
		borderWidth: 1,
		center: ["50%", "50%"],
		colorByPoint: true,
		dataLabels: {
			distance: 30,
			enabled: true,
			formatter: function() {
				return this.point.name
			},
			y: 5
		},
		legendType: "point",
		marker: null,
		size: "75%",
		showInLegend: false,
		slicedOffset: 10,
		states: {
			hover: {
				brightness: 0.1,
				shadow: false
			}
		}
	});
	aE();
	var w = function(M) {
			var bg = [],
				L;

			function bi(bj) {
				L = /rgba\(\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*,\s*([0-9]?(?:\.[0-9]+)?)\s*\)/.exec(bj);
				if (L) {
					bg = [p(L[1]), p(L[2]), p(L[3]), parseFloat(L[4], 10)]
				} else {
					L = /#([a-fA-F0-9]{2})([a-fA-F0-9]{2})([a-fA-F0-9]{2})/.exec(bj);
					if (L) {
						bg = [p(L[1], 16), p(L[2], 16), p(L[3], 16), 1]
					}
				}
			}
			function bf(bk) {
				var bj;
				if (bg && !isNaN(bg[0])) {
					if (bk === "rgb") {
						bj = "rgb(" + bg[0] + "," + bg[1] + "," + bg[2] + ")"
					} else {
						if (bk === "a") {
							bj = bg[3]
						} else {
							bj = "rgba(" + bg.join(",") + ")"
						}
					}
				} else {
					bj = M
				}
				return bj
			}
			function bh(bk) {
				if (V(bk) && bk !== 0) {
					var bj;
					for (bj = 0; bj < 3; bj++) {
						bg[bj] += p(bk * 255);
						if (bg[bj] < 0) {
							bg[bj] = 0
						}
						if (bg[bj] > 255) {
							bg[bj] = 255
						}
					}
				}
				return this
			}
			function be(bj) {
				bg[3] = bj;
				return this
			}
			bi(M);
			return {
				get: bf,
				brighten: bh,
				setOpacity: be
			}
		};

	function h() {}
	h.prototype = {
		init: function(L, M) {
			this.element = I.createElementNS(ae, M);
			this.renderer = L
		},
		animate: function(bf, M, L) {
			var be = b(M, n, true);
			if (be) {
				be = merge(be);
				if (L) {
					be.complete = L
				}
				animate(this, bf, be)
			} else {
				this.attr(bf);
				if (L) {
					L()
				}
			}
		},
		attr: function(bf, bw) {
			var bu, bp, bs, bk, bg = this.element,
				M = bg.nodeName,
				bq = this.renderer,
				bn, be = this.shadows,
				bm = this.htmlNode,
				bj, bt = this;
			if (a1(bf) && j(bw)) {
				bu = bf;
				bf = {};
				bf[bu] = bw
			}
			if (a1(bf)) {
				bu = bf;
				if (M === "circle") {
					bu = {
						x: "cx",
						y: "cy"
					}[bu] || bu
				} else {
					if (bu === "strokeWidth") {
						bu = "stroke-width"
					}
				}
				bt = ac(bg, bu) || this[bu] || 0;
				if (bu !== "d" && bu !== "visibility") {
					bt = parseFloat(bt)
				}
			} else {
				for (bu in bf) {
					bn = false;
					bp = bf[bu];
					if (bu === "d") {
						if (bp && bp.join) {
							bp = bp.join(" ")
						}
						if (/(NaN| {2}|^$)/.test(bp)) {
							bp = "M 0 0"
						}
						this.d = bp
					} else {
						if (bu === "x" && M === "text") {
							for (bs = 0; bs < bg.childNodes.length; bs++) {
								bk = bg.childNodes[bs];
								if (ac(bk, "x") === ac(bg, "x")) {
									ac(bk, "x", bp)
								}
							}
							if (this.rotation) {
								ac(bg, "transform", "rotate(" + this.rotation + " " + bp + " " + p(bf.y || ac(bg, "y")) + ")")
							}
						} else {
							if (bu === "fill") {
								bp = bq.color(bp, bg, bu)
							} else {
								if (M === "circle" && (bu === "x" || bu === "y")) {
									bu = {
										x: "cx",
										y: "cy"
									}[bu] || bu
								} else {
									if (bu === "translateX" || bu === "translateY" || bu === "rotation" || bu === "verticalAlign") {
										this[bu] = bp;
										this.updateTransform();
										bn = true
									} else {
										if (bu === "stroke") {
											bp = bq.color(bp, bg, bu)
										} else {
											if (bu === "dashstyle") {
												bu = "stroke-dasharray";
												bp = bp && bp.toLowerCase();
												if (bp === "solid") {
													bp = ah
												} else {
													if (bp) {
														bp = bp.replace("shortdashdotdot", "3,1,1,1,1,1,").replace("shortdashdot", "3,1,1,1").replace("shortdot", "1,1,").replace("shortdash", "3,1,").replace("longdash", "8,3,").replace(/dot/g, "1,3,").replace("dash", "4,3,").replace(/,$/, "").split(",");
														bs = bp.length;
														while (bs--) {
															bp[bs] = p(bp[bs]) * bf["stroke-width"]
														}
														bp = bp.join(",")
													}
												}
											} else {
												if (bu === "isTracker") {
													this[bu] = bp
												} else {
													if (bu === "width") {
														bp = p(bp)
													} else {
														if (bu === "align") {
															bu = "text-anchor";
															bp = {
																left: "start",
																center: "middle",
																right: "end"
															}[bp]
														} else {
															if (bu === "title") {
																var bv = I.createElementNS(ae, "title");
																bv.appendChild(I.createTextNode(bp));
																bg.appendChild(bv)
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
					if (bu === "strokeWidth") {
						bu = "stroke-width"
					}
					if (az && bu === "stroke-width" && bp === 0) {
						bp = 0.000001
					}
					if (this.symbolName && /^(x|y|r|start|end|innerR)/.test(bu)) {
						if (!bj) {
							this.symbolAttr(bf);
							bj = true
						}
						bn = true
					}
					if (be && /^(width|height|visibility|x|y|d)$/.test(bu)) {
						bs = be.length;
						while (bs--) {
							ac(be[bs], bu, bp)
						}
					}
					if ((bu === "width" || bu === "height") && M === "rect" && bp < 0) {
						bp = 0
					}
					if (bu === "text") {
						this.textStr = bp;
						if (this.added) {
							bq.buildText(this)
						}
					} else {
						if (!bn) {
							ac(bg, bu, bp)
						}
					}
					if (bm && (bu === "x" || bu === "y" || bu === "translateX" || bu === "translateY" || bu === "visibility")) {
						var bi = this,
							bl, L = bm.length ? bm : [this],
							bh = L.length,
							bo, br;
						for (br = 0; br < bh; br++) {
							bo = L[br];
							bl = bo.getBBox();
							bm = bo.htmlNode;
							ax(bm, aw(bi.styles, {
								left: (bl.x + (bi.translateX || 0)) + aj,
								top: (bl.y + (bi.translateY || 0)) + aj
							}));
							if (bu === "visibility") {
								ax(bm, {
									visibility: bp
								})
							}
						}
					}
				}
			}
			return bt
		},
		symbolAttr: function(L) {
			var M = this;
			each(["x", "y", "r", "start", "end", "width", "height", "innerR"], function(be) {
				M[be] = b(L[be], M[be])
			});
			M.attr({
				d: M.renderer.symbols[M.symbolName](u(M.x * 2) / 2, u(M.y * 2) / 2, M.r, {
					start: M.start,
					end: M.end,
					width: M.width,
					height: M.height,
					innerR: M.innerR
				})
			})
		},
		clip: function(L) {
			return this.attr("clip-path", "url(" + this.renderer.url + "#" + L.id + ")")
		},
		crisp: function(bh, bi, bg, M, bl) {
			var L = this,
				bk, bf = {},
				bj = {},
				be;
			bh = bh || L.strokeWidth || 0;
			be = bh % 2 / 2;
			bj.x = R(bi || L.x || 0) + be;
			bj.y = R(bg || L.y || 0) + be;
			bj.width = R((M || L.width || 0) - 2 * be);
			bj.height = R((bl || L.height || 0) - 2 * be);
			bj.strokeWidth = bh;
			for (bk in bj) {
				if (L[bk] !== bj[bk]) {
					L[bk] = bf[bk] = bj[bk]
				}
			}
			return bf
		},
		css: function(bf) {
			var L = this,
				be = L.element,
				bh = bf && bf.width && be.nodeName === "text",
				bi, bg = "",
				M = function(bk, bj) {
					return "-" + bj.toLowerCase()
				};
			if (bf && bf.color) {
				bf.fill = bf.color
			}
			bf = aw(L.styles, bf);
			L.styles = bf;
			if (a2 && !c) {
				if (bh) {
					delete bf.width
				}
				ax(L.element, bf)
			} else {
				for (bi in bf) {
					bg += bi.replace(/([A-Z])/g, M) + ":" + bf[bi] + ";"
				}
				L.attr({
					style: bg
				})
			}
			if (bh && L.added) {
				L.renderer.buildText(L)
			}
			return L
		},
		on: function(L, be) {
			var M = be;
			if (aZ && L === "click") {
				L = "touchstart";
				M = function(bf) {
					bf.preventDefault();
					be()
				}
			}
			this.element["on" + L] = M;
			return this
		},
		translate: function(L, M) {
			return this.attr({
				translateX: L,
				translateY: M
			})
		},
		invert: function() {
			var L = this;
			L.inverted = true;
			L.updateTransform();
			return L
		},
		updateTransform: function() {
			var bh = this,
				bg = bh.translateX || 0,
				bf = bh.translateY || 0,
				L = bh.inverted,
				be = bh.rotation,
				M = [];
			if (L) {
				bg += bh.attr("width");
				bf += bh.attr("height")
			}
			if (bg || bf) {
				M.push("translate(" + bg + "," + bf + ")")
			}
			if (L) {
				M.push("rotate(90) scale(-1,1)")
			} else {
				if (be) {
					M.push("rotate(" + be + " " + bh.x + " " + bh.y + ")")
				}
			}
			if (M.length) {
				ac(bh.element, "transform", M.join(" "))
			}
		},
		toFront: function() {
			var L = this.element;
			L.parentNode.appendChild(L);
			return this
		},
		align: function(M, bh, be) {
			var bk = this;
			if (!M) {
				M = bk.alignOptions;
				bh = bk.alignByTranslate
			} else {
				bk.alignOptions = M;
				bk.alignByTranslate = bh;
				if (!be) {
					bk.renderer.alignedObjects.push(bk)
				}
			}
			be = b(be, bk.renderer);
			var bg = M.align,
				bf = M.verticalAlign,
				bj = (be.x || 0) + (M.x || 0),
				bi = (be.y || 0) + (M.y || 0),
				L = {};
			if (/^(right|center)$/.test(bg)) {
				bj += (be.width - (M.width || 0)) / {
					right: 1,
					center: 2
				}[bg]
			}
			L[bh ? "translateX" : "x"] = u(bj);
			if (/^(bottom|middle)$/.test(bf)) {
				bi += (be.height - (M.height || 0)) / ({
					bottom: 1,
					middle: 2
				}[bf] || 1)
			}
			L[bh ? "translateY" : "y"] = u(bi);
			bk[bk.placed ? "animate" : "attr"](L);
			bk.placed = true;
			bk.alignAttr = L;
			return bk
		},
		getBBox: function() {
			var bg, bf, M, be = this.rotation,
				L = be * bb;
			try {
				bg = aw({}, this.element.getBBox())
			} catch (bh) {
				bg = {
					width: 0,
					height: 0
				}
			}
			bf = bg.width;
			M = bg.height;
			if (be) {
				bg.width = ba(M * C(L)) + ba(bf * aU(L));
				bg.height = ba(M * aU(L)) + ba(bf * C(L))
			}
			return bg
		},
		show: function() {
			return this.attr({
				visibility: al
			})
		},
		hide: function() {
			return this.attr({
				visibility: at
			})
		},
		add: function(bj) {
			var bh = this.renderer,
				bg = bj || bh,
				bf = bg.element || bh.box,
				bl = bf.childNodes,
				be = this.element,
				bi = ac(be, "zIndex"),
				bk, L, M;
			this.parentInverted = bj && bj.inverted;
			if (this.textStr !== undefined) {
				bh.buildText(this)
			}
			if (bj && this.htmlNode) {
				if (!bj.htmlNode) {
					bj.htmlNode = []
				}
				bj.htmlNode.push(this)
			}
			if (bi) {
				bg.handleZ = true;
				bi = p(bi)
			}
			if (bg.handleZ) {
				for (M = 0; M < bl.length; M++) {
					bk = bl[M];
					L = ac(bk, "zIndex");
					if (bk !== be && (p(L) > bi || (!j(bi) && j(L)))) {
						bf.insertBefore(be, bk);
						return this
					}
				}
			}
			bf.appendChild(be);
			this.added = true;
			return this
		},
		safeRemoveChild: function(M) {
			var L = M.parentNode;
			if (L) {
				L.removeChild(M)
			}
		},
		destroy: function() {
			var bg = this,
				be = bg.element || {},
				bf = bg.shadows,
				M, L;
			be.onclick = be.onmouseout = be.onmouseover = be.onmousemove = null;
			stop(bg);
			if (bg.clipPath) {
				bg.clipPath = bg.clipPath.destroy()
			}
			if (bg.stops) {
				for (L = 0; L < bg.stops.length; L++) {
					bg.stops[L] = bg.stops[L].destroy()
				}
				bg.stops = null
			}
			bg.safeRemoveChild(be);
			if (bf) {
				each(bf, function(bh) {
					bg.safeRemoveChild(bh)
				})
			}
			aO(bg.renderer.alignedObjects, bg);
			for (M in bg) {
				delete bg[M]
			}
			return null
		},
		empty: function() {
			var M = this.element,
				be = M.childNodes,
				L = be.length;
			while (L--) {
				M.removeChild(be[L])
			}
		},
		shadow: function(L, bh) {
			var bg = [],
				bf, bi, be = this.element,
				M = this.parentInverted ? "(-1,-1)" : "(1,1)";
			if (L) {
				for (bf = 1; bf <= 3; bf++) {
					bi = be.cloneNode(0);
					ac(bi, {
						isShadow: "true",
						stroke: "rgb(0, 0, 0)",
						"stroke-opacity": 0.05 * bf,
						"stroke-width": 7 - 2 * bf,
						transform: "translate" + M,
						fill: ah
					});
					if (bh) {
						bh.element.appendChild(bi)
					} else {
						be.parentNode.insertBefore(bi, be)
					}
					bg.push(bi)
				}
				this.shadows = bg
			}
			return this
		}
	};
	var q = function() {
			this.init.apply(this, arguments)
		};
	q.prototype = {
		Element: h,
		init: function(be, bf, M, bh) {
			var bg = this,
				bi = location,
				L;
			L = bg.createElement("svg").attr({
				xmlns: ae,
				version: "1.1"
			});
			be.appendChild(L.element);
			bg.box = L.element;
			bg.boxWrapper = L;
			bg.alignedObjects = [];
			bg.url = a2 ? "" : bi.href.replace(/#.*?$/, "");
			bg.defs = this.createElement("defs").add();
			bg.forExport = bh;
			bg.gradients = [];
			bg.setSize(bf, M, false)
		},
		destroy: function() {
			var be = this,
				L, M = be.gradients,
				bf = be.defs;
			be.box = null;
			be.boxWrapper = be.boxWrapper.destroy();
			if (M) {
				for (L = 0; L < M.length; L++) {
					be.gradients[L] = M[L].destroy()
				}
				be.gradients = null
			}
			if (bf) {
				be.defs = bf.destroy()
			}
			be.alignedObjects = null;
			return null
		},
		createElement: function(M) {
			var L = new this.Element();
			L.init(this, M);
			return L
		},
		buildText: function(L) {
			var bf = L.element,
				bq = b(L.textStr, "").toString().replace(/<(b|strong)>/g, '<span style="font-weight:bold">').replace(/<(i|em)>/g, '<span style="font-style:italic">').replace(/<a/g, "<span").replace(/<\/(b|strong|i|em|a)>/g, "</span>").split(/<br.*?>/g),
				bn = bf.childNodes,
				bp = /style="([^"]+)"/,
				be = /href="([^"]+)"/,
				bo = ac(bf, "x"),
				bj = L.styles,
				bi = bj && L.useHTML && !this.forExport,
				bl = L.htmlNode,
				M = bj && p(bj.width),
				bm = bj && bj.lineHeight,
				bk, bg = "getComputedStyle",
				bh = bn.length;
			while (bh--) {
				bf.removeChild(bn[bh])
			}
			if (M && !L.added) {
				this.box.appendChild(bf)
			}
			each(bq, function(br, bv) {
				var bt, bs = 0,
					bu;
				br = br.replace(/<span/g, "|||<span").replace(/<\/span>/g, "</span>|||");
				bt = br.split("|||");
				each(bt, function(by) {
					if (by !== "" || bt.length === 1) {
						var bw = {},
							bA = I.createElementNS(ae, "tspan");
						if (bp.test(by)) {
							ac(bA, "style", by.match(bp)[1].replace(/(;| |^)color([ :])/, "$1fill$2"))
						}
						if (be.test(by)) {
							ac(bA, "onclick", 'location.href="' + by.match(be)[1] + '"');
							ax(bA, {
								cursor: "pointer"
							})
						}
						by = (by.replace(/<(.|\n)*?>/g, "") || " ").replace(/&lt;/g, "<").replace(/&gt;/g, ">");
						bA.appendChild(I.createTextNode(by));
						if (!bs) {
							bw.x = bo
						} else {
							bw.dx = 3
						}
						if (!bs) {
							if (bv) {
								if (!c && L.renderer.forExport) {
									ax(bA, {
										display: "block"
									})
								}
								bu = aD[bg] && p(aD[bg](bk, null).getPropertyValue("line-height"));
								if (!bu || isNaN(bu)) {
									bu = bm || bk.offsetHeight || 18
								}
								ac(bA, "dy", bu)
							}
							bk = bA
						}
						ac(bA, bw);
						bf.appendChild(bA);
						bs++;
						if (M) {
							var bB = by.replace(/-/g, "- ").split(" "),
								bC, bz, bx = [];
							while (bB.length || bx.length) {
								bz = bf.getBBox().width;
								bC = bz > M;
								if (!bC || bB.length === 1) {
									bB = bx;
									bx = [];
									if (bB.length) {
										bA = I.createElementNS(ae, "tspan");
										ac(bA, {
											dy: bm || 16,
											x: bo
										});
										bf.appendChild(bA);
										if (bz > M) {
											M = bz
										}
									}
								} else {
									bA.removeChild(bA.firstChild);
									bx.unshift(bB.pop())
								}
								if (bB.length) {
									bA.appendChild(I.createTextNode(bB.join(" ").replace(/- /g, "-")))
								}
							}
						}
					}
				})
			});
			if (bi) {
				if (!bl) {
					bl = L.htmlNode = ar("span", null, aw(bj, {
						position: a0,
						top: 0,
						left: 0
					}), this.box.parentNode)
				}
				bl.innerHTML = L.textStr;
				bh = bn.length;
				while (bh--) {
					bn[bh].style.visibility = at
				}
			}
		},
		crispLine: function(M, L) {
			if (M[1] === M[4]) {
				M[1] = M[4] = u(M[1]) + (L % 2 / 2)
			}
			if (M[2] === M[5]) {
				M[2] = M[5] = u(M[2]) + (L % 2 / 2)
			}
			return M
		},
		path: function(L) {
			return this.createElement("path").attr({
				d: L,
				fill: ah
			})
		},
		circle: function(M, bf, be) {
			var L = F(M) ? M : {
				x: M,
				y: bf,
				r: be
			};
			return this.createElement("circle").attr(L)
		},
		arc: function(L, bh, bf, be, bg, M) {
			if (F(L)) {
				bh = L.y;
				bf = L.r;
				be = L.innerR;
				bg = L.start;
				M = L.end;
				L = L.x
			}
			return this.symbol("arc", L || 0, bh || 0, bf || 0, {
				innerR: be || 0,
				start: bg || 0,
				end: M || 0
			})
		},
		rect: function(M, bi, be, L, bf, bh) {
			if (F(M)) {
				bi = M.y;
				be = M.width;
				L = M.height;
				bf = M.r;
				bh = M.strokeWidth;
				M = M.x
			}
			var bg = this.createElement("rect").attr({
				rx: bf,
				ry: bf,
				fill: ah
			});
			return bg.attr(bg.crisp(bh, M, bi, aQ(be, 0), aQ(L, 0)))
		},
		setSize: function(bg, L, be) {
			var bh = this,
				M = bh.alignedObjects,
				bf = M.length;
			bh.width = bg;
			bh.height = L;
			bh.boxWrapper[b(be, true) ? "animate" : "attr"]({
				width: bg,
				height: L
			});
			while (bf--) {
				M[bf].align()
			}
		},
		g: function(L) {
			var M = this.createElement("g");
			return j(L) ? M.attr({
				"class": z + L
			}) : M
		},
		image: function(bh, M, bi, bf, L) {
			var bg = {
				preserveAspectRatio: ah
			},
				be;
			if (arguments.length > 1) {
				aw(bg, {
					x: M,
					y: bi,
					width: bf,
					height: L
				})
			}
			be = this.createElement("image").attr(bg);
			if (be.element.setAttributeNS) {
				be.element.setAttributeNS("http://www.w3.org/1999/xlink", "href", bh)
			} else {
				be.element.setAttribute("hc-svg-href", bh)
			}
			return be
		},
		symbol: function(be, bl, bj, bh, bn) {
			var bg, M = this.symbols[be],
				bm = M && M(u(bl), u(bj), bh, bn),
				bf = /^url\((.*?)\)$/,
				bk, bi;
			if (bm) {
				bg = this.path(bm);
				aw(bg, {
					symbolName: be,
					x: bl,
					y: bj,
					r: bh
				});
				if (bn) {
					aw(bg, bn)
				}
			} else {
				if (bf.test(be)) {
					var L = function(bo, bp) {
							bo.attr({
								width: bp[0],
								height: bp[1]
							}).translate(-u(bp[0] / 2), -u(bp[1] / 2))
						};
					bk = be.match(bf)[1];
					bi = aq[bk];
					bg = this.image(bk).attr({
						x: bl,
						y: bj
					});
					if (bi) {
						L(bg, bi)
					} else {
						bg.attr({
							width: 0,
							height: 0
						});
						ar("img", {
							onload: function() {
								var bo = this;
								L(bg, aq[bk] = [bo.width, bo.height])
							},
							src: bk
						})
					}
				} else {
					bg = this.circle(bl, bj, bh)
				}
			}
			return bg
		},
		symbols: {
			square: function(be, bf, M) {
				var L = 0.707 * M;
				return [au, be - L, bf - L, av, be + L, bf - L, be + L, bf + L, be - L, bf + L, "Z"]
			},
			triangle: function(M, be, L) {
				return [au, M, be - 1.33 * L, av, M + L, be + 0.67 * L, M - L, be + 0.67 * L, "Z"]
			},
			"triangle-down": function(M, be, L) {
				return [au, M, be + 1.33 * L, av, M - L, be - 0.67 * L, M + L, be - 0.67 * L, "Z"]
			},
			diamond: function(M, be, L) {
				return [au, M, be - L, av, M + L, be, M, be + L, M - L, be, "Z"]
			},
			arc: function(bl, bk, bi, bn) {
				var be = bn.start,
					bf = bn.end - 0.000001,
					bj = bn.innerR,
					bh = aU(be),
					L = C(be),
					M = aU(bf),
					bm = C(bf),
					bg = bn.end - be < o ? 0 : 1;
				return [au, bl + bi * bh, bk + bi * L, "A", bi, bi, 0, bg, 1, bl + bi * M, bk + bi * bm, av, bl + bj * M, bk + bj * bm, "A", bj, bj, 0, bg, 0, bl + bj * bh, bk + bj * L, "Z"]
			}
		},
		clipRect: function(M, bi, be, L) {
			var bh, bg = z + Q++,
				bf = this.createElement("clipPath").attr({
					id: bg
				}).add(this.defs);
			bh = this.rect(M, bi, be, L, 0).add(bf);
			bh.id = bg;
			bh.clipPath = bf;
			return bh
		},
		color: function(bh, bg, M) {
			var bn, bf = /^rgba/;
			if (bh && bh.linearGradient) {
				var bl = this,
					bi = "linearGradient",
					bm = bh[bi],
					be = z + Q++,
					L, bk, bj;
				L = bl.createElement(bi).attr({
					id: be,
					gradientUnits: "userSpaceOnUse",
					x1: bm[0],
					y1: bm[1],
					x2: bm[2],
					y2: bm[3]
				}).add(bl.defs);
				bl.gradients.push(L);
				L.stops = [];
				each(bh.stops, function(bo) {
					var bp;
					if (bf.test(bo[1])) {
						bn = w(bo[1]);
						bk = bn.get("rgb");
						bj = bn.get("a")
					} else {
						bk = bo[1];
						bj = 1
					}
					bp = bl.createElement("stop").attr({
						offset: bo[0],
						"stop-color": bk,
						"stop-opacity": bj
					}).add(L);
					L.stops.push(bp)
				});
				return "url(" + this.url + "#" + be + ")"
			} else {
				if (bf.test(bh)) {
					bn = w(bh);
					ac(bg, M + "-opacity", bn.get("a"));
					return bn.get("rgb")
				} else {
					bg.removeAttribute(M + "-opacity");
					return bh
				}
			}
		},
		text: function(bf, L, bh, be) {
			var M = aK.chart.style,
				bg;
			L = u(b(L, 0));
			bh = u(b(bh, 0));
			bg = this.createElement("text").attr({
				x: L,
				y: bh,
				text: bf
			}).css({
				fontFamily: M.fontFamily,
				fontSize: M.fontSize
			});
			bg.x = L;
			bg.y = bh;
			bg.useHTML = be;
			return bg
		}
	};
	aG = q;
	var a8;
	if (!c) {
		var aN = aP(h, {
			init: function(be, bf) {
				var L = ["<", bf, ' filled="f" stroked="f"'],
					M = ["position: ", a0, ";"];
				if (bf === "shape" || bf === an) {
					M.push("left:0;top:0;width:10px;height:10px;")
				}
				if (aX) {
					M.push("visibility: ", bf === an ? at : al)
				}
				L.push(' style="', M.join(""), '"/>');
				if (bf) {
					L = bf === an || bf === "span" || bf === "img" ? L.join("") : be.prepVML(L);
					this.element = ar(L)
				}
				this.renderer = be
			},
			add: function(bf) {
				var bi = this,
					bh = bi.renderer,
					be = bi.element,
					bg = bh.box,
					M = bf && bf.inverted,
					L = bf ? bf.element || bf : bg;
				if (M) {
					bh.invertChild(be, L)
				}
				if (aX && L.gVis === at) {
					ax(be, {
						visibility: at
					})
				}
				L.appendChild(be);
				bi.added = true;
				if (bi.alignOnAdd) {
					bi.updateTransform()
				}
				return bi
			},
			attr: function(bg, L) {
				var br, bq, bh, bi = this.element || {},
					M = bi.style,
					bp = bi.nodeName,
					bm = this.renderer,
					bf = this.symbolName,
					bs, bl, be = this.shadows,
					bn, bj = this;
				if (a1(bg) && j(L)) {
					br = bg;
					bg = {};
					bg[br] = L
				}
				if (a1(bg)) {
					br = bg;
					if (br === "strokeWidth" || br === "stroke-width") {
						bj = this.strokeweight
					} else {
						bj = this[br]
					}
				} else {
					for (br in bg) {
						bq = bg[br];
						bn = false;
						if (bf && /^(x|y|r|start|end|width|height|innerR)/.test(br)) {
							if (!bl) {
								this.symbolAttr(bg);
								bl = true
							}
							bn = true
						} else {
							if (br === "d") {
								bq = bq || [];
								this.d = bq.join(" ");
								bh = bq.length;
								var bk = [];
								while (bh--) {
									if (V(bq[bh])) {
										bk[bh] = u(bq[bh] * 10) - 5
									} else {
										if (bq[bh] === "Z") {
											bk[bh] = "x"
										} else {
											bk[bh] = bq[bh]
										}
									}
								}
								bq = bk.join(" ") || "x";
								bi.path = bq;
								if (be) {
									bh = be.length;
									while (bh--) {
										be[bh].path = bq
									}
								}
								bn = true
							} else {
								if (br === "zIndex" || br === "visibility") {
									if (aX && br === "visibility" && bp === "DIV") {
										bi.gVis = bq;
										bs = bi.childNodes;
										bh = bs.length;
										while (bh--) {
											ax(bs[bh], {
												visibility: bq
											})
										}
										if (bq === al) {
											bq = null
										}
									}
									if (bq) {
										M[br] = bq
									}
									bn = true
								} else {
									if (/^(width|height)$/.test(br)) {
										this[br] = bq;
										if (this.updateClipping) {
											this[br] = bq;
											this.updateClipping()
										} else {
											M[br] = bq
										}
										bn = true
									} else {
										if (/^(x|y)$/.test(br)) {
											this[br] = bq;
											if (bi.tagName === "SPAN") {
												this.updateTransform()
											} else {
												M[{
													x: "left",
													y: "top"
												}[br]] = bq
											}
										} else {
											if (br === "class") {
												bi.className = bq
											} else {
												if (br === "stroke") {
													bq = bm.color(bq, bi, br);
													br = "strokecolor"
												} else {
													if (br === "stroke-width" || br === "strokeWidth") {
														bi.stroked = bq ? true : false;
														br = "strokeweight";
														this[br] = bq;
														if (V(bq)) {
															bq += aj
														}
													} else {
														if (br === "dashstyle") {
															var bo = bi.getElementsByTagName("stroke")[0] || ar(bm.prepVML(["<stroke/>"]), null, null, bi);
															bo[br] = bq || "solid";
															this.dashstyle = bq;
															bn = true
														} else {
															if (br === "fill") {
																if (bp === "SPAN") {
																	M.color = bq
																} else {
																	bi.filled = bq !== ah ? true : false;
																	bq = bm.color(bq, bi, br);
																	br = "fillcolor"
																}
															} else {
																if (br === "translateX" || br === "translateY" || br === "rotation" || br === "align") {
																	if (br === "align") {
																		br = "textAlign"
																	}
																	this[br] = bq;
																	this.updateTransform();
																	bn = true
																} else {
																	if (br === "text") {
																		this.bBox = null;
																		bi.innerHTML = bq;
																		bn = true
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
						if (be && br === "visibility") {
							bh = be.length;
							while (bh--) {
								be[bh].style[br] = bq
							}
						}
						if (!bn) {
							if (aX) {
								bi[br] = bq
							} else {
								ac(bi, br, bq)
							}
						}
					}
				}
				return bj
			},
			clip: function(L) {
				var be = this,
					M = L.members;
				M.push(be);
				be.destroyClip = function() {
					aO(M, be)
				};
				return be.css(L.getCSS(be.inverted))
			},
			css: function(M) {
				var bf = this,
					L = bf.element,
					be = M && L.tagName === "SPAN" && M.width;
				if (be) {
					delete M.width;
					bf.textWidth = be;
					bf.updateTransform()
				}
				bf.styles = aw(bf.styles, M);
				ax(bf.element, M);
				return bf
			},
			safeRemoveChild: function(M) {
				var L = M.parentNode;
				if (L) {
					U(M)
				}
			},
			destroy: function() {
				var L = this;
				if (L.destroyClip) {
					L.destroyClip()
				}
				return h.prototype.destroy.apply(L)
			},
			empty: function() {
				var M = this.element,
					bf = M.childNodes,
					L = bf.length,
					be;
				while (L--) {
					be = bf[L];
					be.parentNode.removeChild(be)
				}
			},
			getBBox: function() {
				var be = this,
					L = be.element,
					M = be.bBox;
				if (!M) {
					if (L.nodeName === "text") {
						L.style.position = a0
					}
					M = be.bBox = {
						x: L.offsetLeft,
						y: L.offsetTop,
						width: L.offsetWidth,
						height: L.offsetHeight
					}
				}
				return M
			},
			on: function(L, M) {
				this.element["on" + L] = function() {
					var be = aD.event;
					be.target = be.srcElement;
					M(be)
				};
				return this
			},
			updateTransform: function() {
				if (!this.added) {
					this.alignOnAdd = true;
					return
				}
				var bf = this,
					bt = bf.element,
					bw = bf.translateX || 0,
					bv = bf.translateY || 0,
					bk = bf.x || 0,
					bi = bf.y || 0,
					br = bf.textAlign || "left",
					bl = {
						left: 0,
						center: 0.5,
						right: 1
					}[br],
					bg = br && br !== "left";
				if (bw || bv) {
					bf.css({
						marginLeft: bw,
						marginTop: bv
					})
				}
				if (bf.inverted) {
					each(bt.childNodes, function(bx) {
						bf.renderer.invertChild(bx, bt)
					})
				}
				if (bt.tagName === "SPAN") {
					var bp, bn, bq = bf.rotation,
						M, bu = 0,
						be = 1,
						bm = 0,
						bj, L = p(bf.textWidth),
						bs = bf.xCorr || 0,
						bh = bf.yCorr || 0,
						bo = [bq, br, bt.innerHTML, bf.textWidth].join(",");
					if (bo !== bf.cTT) {
						if (j(bq)) {
							bu = bq * bb;
							be = aU(bu);
							bm = C(bu);
							ax(bt, {
								filter: bq ? ["progid:DXImageTransform.Microsoft.Matrix(M11=", be, ", M12=", -bm, ", M21=", bm, ", M22=", be, ", sizingMethod='auto expand')"].join("") : ah
							})
						}
						bp = bt.offsetWidth;
						bn = bt.offsetHeight;
						if (bp > L) {
							ax(bt, {
								width: L + aj,
								display: "block",
								whiteSpace: "normal"
							});
							bp = L
						}
						M = u((p(bt.style.fontSize) || 12) * 1.2);
						bs = be < 0 && -bp;
						bh = bm < 0 && -bn;
						bj = be * bm < 0;
						bs += bm * M * (bj ? 1 - bl : bl);
						bh -= be * M * (bq ? (bj ? bl : 1 - bl) : 1);
						if (bg) {
							bs -= bp * bl * (be < 0 ? -1 : 1);
							if (bq) {
								bh -= bn * bl * (bm < 0 ? -1 : 1)
							}
							ax(bt, {
								textAlign: br
							})
						}
						bf.xCorr = bs;
						bf.yCorr = bh
					}
					ax(bt, {
						left: bk + bs,
						top: bi + bh
					});
					bf.cTT = bo
				}
			},
			shadow: function(bj, bi) {
				var M = [],
					be, bf = this.element,
					bg = this.renderer,
					bh, L = bf.style,
					bk, bl = bf.path;
				if (bl && typeof bl.value !== "string") {
					bl = "x"
				}
				if (bj) {
					for (be = 1; be <= 3; be++) {
						bk = ['<shape isShadow="true" strokeweight="', (7 - 2 * be), '" filled="false" path="', bl, '" coordsize="100,100" style="', bf.style.cssText, '" />'];
						bh = ar(bg.prepVML(bk), null, {
							left: p(L.left) + 1,
							top: p(L.top) + 1
						});
						bk = ['<stroke color="black" opacity="', (0.05 * be), '"/>'];
						ar(bg.prepVML(bk), null, null, bh);
						if (bi) {
							bi.element.appendChild(bh)
						} else {
							bf.parentNode.insertBefore(bh, bf)
						}
						M.push(bh)
					}
					this.shadows = M
				}
				return this
			}
		});
		a8 = function() {
			this.init.apply(this, arguments)
		};
		a8.prototype = merge(q.prototype, {
			Element: aN,
			isIE8: e.indexOf("MSIE 8.0") > -1,
			init: function(be, bf, M) {
				var bg = this,
					L;
				bg.alignedObjects = [];
				L = bg.createElement(an);
				be.appendChild(L.element);
				bg.box = L.element;
				bg.boxWrapper = L;
				bg.setSize(bf, M, false);
				if (!I.namespaces.hcv) {
					I.namespaces.add("hcv", "urn:schemas-microsoft-com:vml");
					I.createStyleSheet().cssText = "hcv\\:fill, hcv\\:path, hcv\\:shape, hcv\\:stroke{ behavior:url(#default#VML); display: inline-block; } "
				}
			},
			clipRect: function(M, bg, bf, L) {
				var be = this.createElement();
				return aw(be, {
					members: [],
					left: M,
					top: bg,
					width: bf,
					height: L,
					getCSS: function(bi) {
						var bl = this,
							bn = bl.top,
							bm = bl.left,
							bk = bm + bl.width,
							bh = bn + bl.height,
							bj = {
								clip: "rect(" + u(bi ? bm : bn) + "px," + u(bi ? bh : bk) + "px," + u(bi ? bk : bh) + "px," + u(bi ? bn : bm) + "px)"
							};
						if (!bi && aX) {
							aw(bj, {
								width: bk + aj,
								height: bh + aj
							})
						}
						return bj
					},
					updateClipping: function() {
						each(be.members, function(bh) {
							bh.css(be.getCSS(bh.inverted))
						})
					}
				})
			},
			color: function(bg, be, L) {
				var bo, M = /^rgba/,
					bq;
				if (bg && bg.linearGradient) {
					var bk, bj, bn = bg.linearGradient,
						bf, bp, bi, bm, bh;
					each(bg.stops, function(bs, br) {
						if (M.test(bs[1])) {
							bo = w(bs[1]);
							bk = bo.get("rgb");
							bj = bo.get("a")
						} else {
							bk = bs[1];
							bj = 1
						}
						if (!br) {
							bp = bk;
							bi = bj
						} else {
							bm = bk;
							bh = bj
						}
					});
					bf = 90 - W.atan((bn[3] - bn[1]) / (bn[2] - bn[0])) * 180 / o;
					bq = ["<", L, ' colors="0% ', bp, ",100% ", bm, '" angle="', bf, '" opacity="', bh, '" o:opacity2="', bi, '" type="gradient" focus="100%" />'];
					ar(this.prepVML(bq), null, null, be)
				} else {
					if (M.test(bg) && be.tagName !== "IMG") {
						bo = w(bg);
						bq = ["<", L, ' opacity="', bo.get("a"), '"/>'];
						ar(this.prepVML(bq), null, null, be);
						return bo.get("rgb")
					} else {
						var bl = be.getElementsByTagName(L);
						if (bl.length) {
							bl[0].opacity = 1
						}
						return bg
					}
				}
			},
			prepVML: function(L) {
				var M = "display:inline-block;behavior:url(#default#VML);",
					be = this.isIE8;
				L = L.join("");
				if (be) {
					L = L.replace("/>", ' xmlns="urn:schemas-microsoft-com:vml" />');
					if (L.indexOf('style="') === -1) {
						L = L.replace("/>", ' style="' + M + '" />')
					} else {
						L = L.replace('style="', 'style="' + M)
					}
				} else {
					L = L.replace("<", "<hcv:")
				}
				return L
			},
			text: function(be, L, bf) {
				var M = aK.chart.style;
				return this.createElement("span").attr({
					text: be,
					x: u(L),
					y: u(bf)
				}).css({
					whiteSpace: "nowrap",
					fontFamily: M.fontFamily,
					fontSize: M.fontSize
				})
			},
			path: function(L) {
				return this.createElement("shape").attr({
					coordsize: "100 100",
					d: L
				})
			},
			circle: function(L, be, M) {
				return this.symbol("circle").attr({
					x: L,
					y: be,
					r: M
				})
			},
			g: function(L) {
				var be, M;
				if (L) {
					M = {
						className: z + L,
						"class": z + L
					}
				}
				be = this.createElement(an).attr(M);
				return be
			},
			image: function(bg, M, bh, be, L) {
				var bf = this.createElement("img").attr({
					src: bg
				});
				if (arguments.length > 1) {
					bf.css({
						left: M,
						top: bh,
						width: be,
						height: L
					})
				}
				return bf
			},
			rect: function(M, bi, be, L, bf, bh) {
				if (F(M)) {
					bi = M.y;
					be = M.width;
					L = M.height;
					bf = M.r;
					bh = M.strokeWidth;
					M = M.x
				}
				var bg = this.symbol("rect");
				bg.r = bf;
				return bg.attr(bg.crisp(bh, M, bi, aQ(be, 0), aQ(L, 0)))
			},
			invertChild: function(M, L) {
				var be = L.style;
				ax(M, {
					flip: "x",
					left: p(be.width) - 10,
					top: p(be.height) - 10,
					rotation: -90
				})
			},
			symbols: {
				arc: function(bl, bk, bi, bo) {
					var be = bo.start,
						bf = bo.end,
						bh = aU(be),
						L = C(be),
						M = aU(bf),
						bm = C(bf),
						bj = bo.innerR,
						bn = 0.07 / bi,
						bg = (bj && 0.1 / bj) || 0;
					if (bf - be === 0) {
						return ["x"]
					} else {
						if (2 * o - bf + be < bn) {
							M = -bn
						} else {
							if (bf - be < bg) {
								M = aU(be + bg)
							}
						}
					}
					return ["wa", bl - bi, bk - bi, bl + bi, bk + bi, bl + bi * bh, bk + bi * L, bl + bi * M, bk + bi * bm, "at", bl - bj, bk - bj, bl + bj, bk + bj, bl + bj * M, bk + bj * bm, bl + bj * bh, bk + bj * L, "x", "e"]
				},
				circle: function(L, be, M) {
					return ["wa", L - M, be - M, L + M, be + M, L + M, be, L + M, be, "e"]
				},
				rect: function(bj, bi, bh, be) {
					if (!j(be)) {
						return []
					}
					var bg = be.width,
						L = be.height,
						bf = bj + bg,
						M = bi + L;
					bh = ad(bh, bg, L);
					return [au, bj + bh, bi, av, bf - bh, bi, "wa", bf - 2 * bh, bi, bf, bi + 2 * bh, bf - bh, bi, bf, bi + bh, av, bf, M - bh, "wa", bf - 2 * bh, M - 2 * bh, bf, M, bf, M - bh, bf - bh, M, av, bj + bh, M, "wa", bj, M - 2 * bh, bj + 2 * bh, M, bj + bh, M, bj, M - bh, av, bj, bi + bh, "wa", bj, bi, bj + 2 * bh, bi + 2 * bh, bj, bi + bh, bj + bh, bi, "x", "e"]
				}
			}
		});
		aG = a8
	}
	function a3(bZ, bD) {
		am = merge(am, aK.xAxis);
		aV = merge(aV, aK.yAxis);
		aK.xAxis = aK.yAxis = null;
		bZ = merge(aK, bZ);
		var bE = bZ.chart,
			cd = bE.margin,
			cq = F(cd) ? cd : [cd, cd, cd, cd],
			bB = b(bE.marginTop, cq[0]),
			bz = b(bE.marginRight, cq[1]),
			bS = b(bE.marginBottom, cq[2]),
			bV = b(bE.marginLeft, cq[3]),
			cF = bE.spacingTop,
			bL = bE.spacingRight,
			bG = bE.spacingBottom,
			cu = bE.spacingLeft,
			bT, bW, ce, ci, co, bU, M, br, bK, cj, cc, bO, cv, bR, cG, bw, bs, bX, bx, bj, b2, bn, ct = this,
			bJ = bE.events,
			cH = bJ && !! bJ.click,
			bA, by, bp, bF, bg, cr, cE, bo, be, L, b9, bI, cB, bu, bm, b5, b7 = bE.showAxes,
			bC = 0,
			ck = [],
			bh, b3 = [],
			bl, b0, cg, b8, bt, b6, cp, bq, cn, bk, bi, bN;

		function bv(dF) {
			var dQ = dF.isX,
				dy = dF.opposite,
				cN = bl ? !dQ : dQ,
				dR = cN ? (dy ? 0 : 2) : (dy ? 1 : 3),
				dl = {},
				dn = merge(dQ ? am : aV, [t, B, a4, ag][dR], dF),
				cT = this,
				dN, di = dn.type,
				dO = di === "datetime",
				c9 = di === "logarithmic",
				cX = dn.offset || 0,
				ds = dQ ? "x" : "y",
				c0, du, dc, dt = cN ? M : bU,
				cZ, cS, c4, dK, dM, dP, c1, dG, cJ, c6, c3 = null,
				dS = null,
				dm, dC, cR = dn.minPadding,
				cK = dn.maxPadding,
				c2 = j(dn.linkedTo),
				dV, dJ, dz, c7 = dn.events,
				db, cU = [],
				cV, dv, dL, dI, dD = {},
				dX = {},
				cW = {},
				cP, dT, dj, dU, dB = dn.categories,
				dE = dn.labels.formatter ||
			function() {
				var d0 = this.value,
					dZ;
				if (dU) {
					dZ = aB(dU, d0)
				} else {
					if (cV % 1000000 === 0) {
						dZ = (d0 / 1000000) + "M"
					} else {
						if (cV % 1000 === 0) {
							dZ = (d0 / 1000) + "k"
						} else {
							if (!dB && d0 >= 1000) {
								dZ = i(d0, 0)
							} else {
								dZ = d0
							}
						}
					}
				}
				return dZ
			}, dq = cN && dn.labels.staggerLines, cO = dn.reversed, c5 = (dB && dn.tickmarkPlacement === "between") ? 0.5 : 0;

			function c8(d1, d0) {
				var dZ = this;
				dZ.pos = d1;
				dZ.minor = d0;
				dZ.isNew = true;
				if (!d0) {
					dZ.addLabel()
				}
			}
			c8.prototype = {
				addLabel: function() {
					var d6 = this.pos,
						dZ = dn.labels,
						d5, d2 = !((d6 === dS && !b(dn.showFirstLabel, 1)) || (d6 === c3 && !b(dn.showLastLabel, 0))),
						d3 = (dB && cN && dB.length && !dZ.step && !dZ.staggerLines && !dZ.rotation && be / dB.length) || (!cN && be / 2),
						d1, d4 = dB && j(dB[d6]) ? dB[d6] : d6,
						d0 = this.label;
					d5 = dE.call({
						isFirst: d6 === dI[0],
						isLast: d6 === dI[dI.length - 1],
						dateTimeLabelFormat: dU,
						value: c9 ? aJ(d4) : d4
					});
					d1 = d3 && {
						width: aQ(1, u(d3 - 2 * (dZ.padding || 10))) + aj
					};
					d1 = aw(d1, dZ.style);
					if (d0 === aA) {
						this.label = j(d5) && d2 && dZ.enabled ? b0.text(d5, 0, 0, dZ.useHTML).attr({
							align: dZ.align,
							rotation: dZ.rotation
						}).css(d1).add(c4) : null
					} else {
						if (d0) {
							d0.attr({
								text: d5
							}).css(d1)
						}
					}
				},
				getLabelSize: function() {
					var dZ = this.label;
					return dZ ? ((this.labelBBox = dZ.getBBox()))[cN ? "height" : "width"] : 0
				},
				render: function(d5, d0) {
					var ek = this,
						ej = !ek.minor,
						d8 = ek.label,
						d4 = ek.pos,
						eh = dn.labels,
						d6 = ek.gridLine,
						eg = ej ? dn.gridLineWidth : dn.minorGridLineWidth,
						ei = ej ? dn.gridLineColor : dn.minorGridLineColor,
						d7 = ej ? dn.gridLineDashStyle : dn.minorGridLineDashStyle,
						el, eb = ek.mark,
						dZ, d1 = ej ? dn.tickLength : dn.minorTickLength,
						d9 = ej ? dn.tickWidth : (dn.minorTickWidth || 0),
						ed = ej ? dn.tickColor : dn.minorTickColor,
						ee = ej ? dn.tickPosition : dn.minorTickPosition,
						d3 = eh.step,
						d2 = (d0 && bX) || bw,
						ef, ec, ea;
					ec = cN ? cZ(d4 + c5, null, null, d0) + dt : M + cX + (dy ? ((d0 && bs) || cG) - co - M : 0);
					ea = cN ? d2 - bU + cX - (dy ? bo : 0) : d2 - cZ(d4 + c5, null, null, d0) - dt;
					if (eg) {
						el = cS(d4 + c5, eg, d0);
						if (d6 === aA) {
							ef = {
								stroke: ei,
								"stroke-width": eg
							};
							if (d7) {
								ef.dashstyle = d7
							}
							if (ej) {
								ef.zIndex = 1
							}
							ek.gridLine = d6 = eg ? b0.path(el).attr(ef).add(dK) : null
						}
						if (!d0 && d6 && el) {
							d6.animate({
								d: el
							})
						}
					}
					if (d9) {
						if (ee === "inside") {
							d1 = -d1
						}
						if (dy) {
							d1 = -d1
						}
						dZ = b0.crispLine([au, ec, ea, av, ec + (cN ? 0 : -d1), ea + (cN ? d1 : 0)], d9);
						if (eb) {
							eb.animate({
								d: dZ
							})
						} else {
							ek.mark = b0.path(dZ).attr({
								stroke: ed,
								"stroke-width": d9
							}).add(c4)
						}
					}
					if (d8 && !isNaN(ec)) {
						ec = ec + eh.x - (c5 && cN ? c5 * du * (cO ? -1 : 1) : 0);
						ea = ea + eh.y - (c5 && !cN ? c5 * du * (cO ? 1 : -1) : 0);
						if (!j(eh.y)) {
							ea += p(d8.styles.lineHeight) * 0.9 - d8.getBBox().height / 2
						}
						if (dq) {
							ea += (d5 / (d3 || 1) % dq) * 16
						}
						if (d3) {
							d8[d5 % d3 ? "hide" : "show"]()
						}
						d8[ek.isNew ? "attr" : "animate"]({
							x: ec,
							y: ea
						})
					}
					ek.isNew = false
				},
				destroy: function() {
					a9(this)
				}
			};

			function dx(d0) {
				var dZ = this;
				if (d0) {
					dZ.options = d0;
					dZ.id = d0.id
				}
				return dZ
			}
			dx.prototype = {
				render: function() {
					var ek = this,
						d2 = ek.options,
						dZ = d2.label,
						d4 = ek.label,
						ed = d2.width,
						d1 = d2.to,
						ei = d2.from,
						ee = d2.value,
						eb, d3 = d2.dashStyle,
						eg = ek.svgElem,
						ec = [],
						ef, d8, d5, ej, d7, d6, eh = d2.color,
						ea = d2.zIndex,
						d0 = d2.events,
						d9;
					if (c9) {
						ei = aY(ei);
						d1 = aY(d1);
						ee = aY(ee)
					}
					if (ed) {
						ec = cS(ee, ed);
						d9 = {
							stroke: eh,
							"stroke-width": ed
						};
						if (d3) {
							d9.dashstyle = d3
						}
					} else {
						if (j(ei) && j(d1)) {
							ei = aQ(ei, dS);
							d1 = ad(d1, c3);
							eb = cS(d1);
							ec = cS(ei);
							if (ec && eb) {
								ec.push(eb[4], eb[5], eb[1], eb[2])
							} else {
								ec = null
							}
							d9 = {
								fill: eh
							}
						} else {
							return
						}
					}
					if (j(ea)) {
						d9.zIndex = ea
					}
					if (eg) {
						if (ec) {
							eg.animate({
								d: ec
							}, null, eg.onGetPath)
						} else {
							eg.hide();
							eg.onGetPath = function() {
								eg.show()
							}
						}
					} else {
						if (ec && ec.length) {
							ek.svgElem = eg = b0.path(ec).attr(d9).add();
							if (d0) {
								ef = function(el) {
									eg.on(el, function(em) {
										d0[el].apply(ek, [em])
									})
								};
								for (d8 in d0) {
									ef(d8)
								}
							}
						}
					}
					if (dZ && j(dZ.text) && ec && ec.length && be > 0 && bo > 0) {
						dZ = merge({
							align: cN && eb && "center",
							x: cN ? !eb && 4 : 10,
							verticalAlign: !cN && eb && "middle",
							y: cN ? eb ? 16 : 10 : eb ? 6 : -4,
							rotation: cN && !eb && 90
						}, dZ);
						if (!d4) {
							ek.label = d4 = b0.text(dZ.text, 0, 0).attr({
								align: dZ.textAlign || dZ.align,
								rotation: dZ.rotation,
								zIndex: ea
							}).css(dZ.style).add()
						}
						d5 = [ec[1], ec[4], b(ec[6], ec[1])];
						ej = [ec[2], ec[5], b(ec[7], ec[2])];
						d7 = ad.apply(W, d5);
						d6 = ad.apply(W, ej);
						d4.align(dZ, false, {
							x: d7,
							y: d6,
							width: aQ.apply(W, d5) - d7,
							height: aQ.apply(W, ej) - d6
						});
						d4.show()
					} else {
						if (d4) {
							d4.hide()
						}
					}
					return ek
				},
				destroy: function() {
					var dZ = this;
					a9(dZ);
					aO(cU, dZ)
				}
			};

			function dd(d1, d0, dZ, d3) {
				var d2 = this;
				d2.isNegative = d0;
				d2.options = d1;
				d2.x = dZ;
				d2.stack = d3;
				d2.alignOptions = {
					align: d1.align || (bl ? (d0 ? "left" : "right") : "center"),
					verticalAlign: d1.verticalAlign || (bl ? "middle" : (d0 ? "bottom" : "top")),
					y: b(d1.y, bl ? 4 : (d0 ? 14 : -6)),
					x: b(d1.x, bl ? (d0 ? -6 : 6) : 0)
				};
				d2.textAlign = d1.textAlign || (bl ? (d0 ? "right" : "left") : "center")
			}
			dd.prototype = {
				destroy: function() {
					a9(this)
				},
				setTotal: function(dZ) {
					this.total = dZ;
					this.cum = dZ
				},
				render: function(d0) {
					var dZ = this,
						d1 = dZ.options.formatter.call(dZ);
					if (dZ.label) {
						dZ.label.attr({
							text: d1,
							visibility: at
						})
					} else {
						dZ.label = ct.renderer.text(d1, 0, 0).css(dZ.options.style).attr({
							align: dZ.textAlign,
							rotation: dZ.options.rotation,
							visibility: at
						}).add(d0)
					}
				},
				setOffset: function(d2, d4) {
					var d7 = this,
						dZ = d7.isNegative,
						d5 = cT.translate(d7.total),
						d1 = cT.translate(0),
						d0 = ba(d5 - d1),
						d6 = ct.xAxis[0].translate(d7.x) + d2,
						d3 = ct.plotHeight,
						d8 = {
							x: bl ? (dZ ? d5 : d5 - d0) : d6,
							y: bl ? d3 - d6 - d4 : (dZ ? (d3 - d5 - d0) : d3 - d5),
							width: bl ? d0 : d4,
							height: bl ? d4 : d0
						};
					if (d7.label) {
						d7.label.align(d7.alignOptions, null, d8).attr({
							visibility: al
						})
					}
				}
			};

			function cM() {
				var dZ = [],
					d0 = [],
					d1;
				dP = c1 = null;
				dG = [];
				each(b3, function(d7) {
					d1 = false;
					each(["xAxis", "yAxis"], function(ea) {
						if (d7.isCartesian && ((ea === "xAxis" && dQ) || (ea === "yAxis" && !dQ)) && ((d7.options[ea] === dn.index) || (d7.options[ea] === aA && dn.index === 0))) {
							d7[ea] = cT;
							dG.push(d7);
							d1 = true
						}
					});
					if (!d7.visible && bE.ignoreHiddenSeries) {
						d1 = false
					}
					if (d1) {
						var d6, d8, d5, d3, d9, d4;
						if (!dQ) {
							d6 = d7.options.stacking;
							dz = d6 === "percent";
							if (d6) {
								d9 = d7.options.stack;
								d3 = d7.type + b(d9, "");
								d4 = "-" + d3;
								d7.stackKey = d3;
								d8 = dZ[d3] || [];
								dZ[d3] = d8;
								d5 = d0[d4] || [];
								d0[d4] = d5
							}
							if (dz) {
								dP = 0;
								c1 = 99
							}
						}
						if (d7.isCartesian) {
							each(d7.data, function(ea) {
								var eh = ea.x,
									eg = ea.y,
									eb = eg < 0,
									ed = eb ? d5 : d8,
									ef = eb ? d4 : d3,
									ec, ee;
								if (dP === null) {
									dP = c1 = ea[ds]
								}
								if (dQ) {
									if (eh > c1) {
										c1 = eh
									} else {
										if (eh < dP) {
											dP = eh
										}
									}
								} else {
									if (j(eg)) {
										if (d6) {
											ed[eh] = j(ed[eh]) ? ed[eh] + eg : eg
										}
										ec = ed ? ed[eh] : eg;
										ee = b(ea.low, ec);
										if (!dz) {
											if (ec > c1) {
												c1 = ec
											} else {
												if (ee < dP) {
													dP = ee
												}
											}
										}
										if (d6) {
											if (!dl[ef]) {
												dl[ef] = {}
											}
											if (!dl[ef][eh]) {
												dl[ef][eh] = new dd(dn.stackLabels, eb, eh, d9)
											}
											dl[ef][eh].setTotal(ec)
										}
									}
								}
							});
							if (/(area|column|bar)/.test(d7.type) && !dQ) {
								var d2 = 0;
								if (dP >= d2) {
									dP = d2;
									dV = true
								} else {
									if (c1 < d2) {
										c1 = d2;
										dJ = true
									}
								}
							}
						}
					}
				})
			}
			cZ = function(d0, d6, d7, d1, d5) {
				var d2 = 1,
					d4 = 0,
					d3 = d1 ? dc : du,
					d8 = d1 ? dm : dS,
					dZ;
				if (!d3) {
					d3 = du
				}
				if (d7) {
					d2 *= -1;
					d4 = c0
				}
				if (cO) {
					d2 *= -1;
					d4 -= d2 * c0
				}
				if (d6) {
					if (cO) {
						d0 = c0 - d0
					}
					dZ = d0 / d3 + d8;
					if (c9 && d5) {
						dZ = aJ(dZ)
					}
				} else {
					if (c9 && d5) {
						d0 = aY(d0)
					}
					dZ = d2 * (d0 - d8) * d3 + d4
				}
				return dZ
			};
			cS = function(d8, d4, d1) {
				var d0, d7, dZ, d6, d3 = cZ(d8, null, null, d1),
					d5 = (d1 && bX) || bw,
					d2 = (d1 && bs) || cG,
					d9;
				d0 = dZ = u(d3 + dt);
				d7 = d6 = u(d5 - d3 - dt);
				if (isNaN(d3)) {
					d9 = true
				} else {
					if (cN) {
						d7 = ci;
						d6 = d5 - bU;
						if (d0 < M || d0 > M + be) {
							d9 = true
						}
					} else {
						d0 = M;
						dZ = d2 - co;
						if (d7 < ci || d7 > ci + bo) {
							d9 = true
						}
					}
				}
				return d9 ? null : b0.crispLine([au, d0, d7, av, dZ, d6], d4 || 0)
			};

			function dW(dZ, d2) {
				var d1, d0;
				dL = d2 ? 1 : W.pow(10, R(W.log(dZ) / W.LN10));
				d1 = dZ / dL;
				if (!d2) {
					d2 = [1, 2, 2.5, 5, 10];
					if (dn.allowDecimals === false || c9) {
						if (dL === 1) {
							d2 = [1, 2, 5, 10]
						} else {
							if (dL <= 0.1) {
								d2 = [1 / dL]
							}
						}
					}
				}
				for (d0 = 0; d0 < d2.length; d0++) {
					dZ = d2[d0];
					if (d1 <= (d2[d0] + (d2[d0 + 1] || d2[d0])) / 2) {
						break
					}
				}
				dZ *= dL;
				return dZ
			}
			function dA() {
				dI = [];
				var ed, d6 = aK.global.useUTC,
					d9 = 1000 / N,
					d3 = 60000 / N,
					dZ = 3600000 / N,
					d8 = 24 * 3600000 / N,
					d7 = 7 * 24 * 3600000 / N,
					eg = 30 * 24 * 3600000 / N,
					d4 = 31556952000 / N,
					eb = [
						["second", d9, [1, 2, 5, 10, 15, 30]],
						["minute", d3, [1, 2, 5, 10, 15, 30]],
						["hour", dZ, [1, 2, 3, 4, 6, 8, 12]],
						["day", d8, [1, 2]],
						["week", d7, [1, 2]],
						["month", eg, [1, 2, 3, 4, 6]],
						["year", d4, null]
					],
					d5 = eb[6],
					eh = d5[1],
					d0 = d5[2];
				for (ed = 0; ed < eb.length; ed++) {
					d5 = eb[ed];
					eh = d5[1];
					d0 = d5[2];
					if (eb[ed + 1]) {
						var ec = (eh * d0[d0.length - 1] + eb[ed + 1][1]) / 2;
						if (cV <= ec) {
							break
						}
					}
				}
				if (eh === d4 && cV < 5 * eh) {
					d0 = [1, 2, 5]
				}
				var ef = dW(cV / eh, d0),
					ei, d2 = new Date(dS * N);
				d2.setMilliseconds(0);
				if (eh >= d9) {
					d2.setSeconds(eh >= d3 ? 0 : ef * R(d2.getSeconds() / ef))
				}
				if (eh >= d3) {
					d2[aH](eh >= dZ ? 0 : ef * R(d2[J]() / ef))
				}
				if (eh >= dZ) {
					d2[G](eh >= d8 ? 0 : ef * R(d2[aR]() / ef))
				}
				if (eh >= d8) {
					d2[aT](eh >= eg ? 1 : ef * R(d2[a5]() / ef))
				}
				if (eh >= eg) {
					d2[H](eh >= d4 ? 0 : ef * R(d2[aS]() / ef));
					ei = d2[ak]()
				}
				if (eh >= d4) {
					ei -= ei % ef;
					d2[y](ei)
				}
				if (eh === d7) {
					d2[aT](d2[a5]() - d2[ai]() + dn.startOfWeek)
				}
				ed = 1;
				ei = d2[ak]();
				var d1 = d2.getTime() / N,
					ee = d2[aS](),
					ea = d2[a5]();
				while (d1 < c3 && ed < be) {
					dI.push(d1);
					if (eh === d4) {
						d1 = aW(ei + ed * ef, 0) / N
					} else {
						if (eh === eg) {
							d1 = aW(ei, ee + ed * ef) / N
						} else {
							if (!d6 && (eh === d8 || eh === d7)) {
								d1 = aW(ei, ee, ea + ed * ef * (eh === d8 ? 1 : 7))
							} else {
								d1 += eh * ef
							}
						}
					}
					ed++
				}
				dI.push(d1);
				dU = dn.dateTimeLabelFormats[d5[0]]
			}
			function dk(d0) {
				var d1, dZ = d0;
				dL = b(dL, W.pow(10, R(W.log(cV) / W.LN10)));
				if (dL < 1) {
					d1 = u(1 / dL) * 10;
					dZ = u(d0 * d1) / d1
				}
				return dZ
			}
			function da() {
				var d0, d1 = dk(R(dS / cV) * cV),
					dZ = dk(bc(c3 / cV) * cV);
				dI = [];
				d0 = dk(d1);
				while (d0 <= dZ) {
					dI.push(d0);
					d0 = dk(d0 + cV)
				}
			}
			function dh() {
				var dZ, d8, d0, d3, d4 = dn.tickInterval,
					d2 = dn.tickPixelInterval,
					d5 = dn.maxZoom || (dQ && !j(dn.min) && !j(dn.max) ? ad(ct.smallestInterval * 5, c1 - dP) : null),
					d6;
				c0 = cN ? be : bo;
				if (c2) {
					d0 = ct[dQ ? "xAxis" : "yAxis"][dn.linkedTo];
					d3 = d0.getExtremes();
					dS = b(d3.min, d3.dataMin);
					c3 = b(d3.max, d3.dataMax)
				} else {
					dS = b(cJ, dn.min, dP);
					c3 = b(c6, dn.max, c1)
				}
				if (c9) {
					dS = aY(dS);
					c3 = aY(c3)
				}
				if (c3 - dS < d5) {
					d6 = (d5 - c3 + dS) / 2;
					dS = aQ(dS - d6, b(dn.min, dS - d6), dP);
					c3 = ad(dS + d5, b(dn.max, dS + d5), c1)
				}
				if (!dB && !dz && !c2 && j(dS) && j(c3)) {
					dZ = (c3 - dS) || 1;
					if (!j(dn.min) && !j(cJ) && cR && (dP < 0 || !dV)) {
						dS -= dZ * cR
					}
					if (!j(dn.max) && !j(c6) && cK && (c1 > 0 || !dJ)) {
						c3 += dZ * cK
					}
				}
				if (dS === c3) {
					cV = 1
				} else {
					if (c2 && !d4 && d2 === d0.options.tickPixelInterval) {
						cV = d0.tickInterval
					} else {
						cV = b(d4, dB ? 1 : (c3 - dS) * d2 / c0)
					}
				}
				if (!dO && !j(dn.tickInterval)) {
					cV = dW(cV)
				}
				cT.tickInterval = cV;
				dv = dn.minorTickInterval === "auto" && cV ? cV / 5 : dn.minorTickInterval;
				if (dO) {
					dA()
				} else {
					da()
				}
				if (!c2) {
					if (dB || (dQ && ct.hasColumn)) {
						d8 = (dB ? 1 : cV) * 0.5;
						if (dB || !j(b(dn.min, cJ))) {
							dS -= d8
						}
						if (dB || !j(b(dn.max, c6))) {
							c3 += d8
						}
					}
					var d1 = dI[0],
						d7 = dI[dI.length - 1];
					if (dn.startOnTick) {
						dS = d1
					} else {
						if (dS > d1) {
							dI.shift()
						}
					}
					if (dn.endOnTick) {
						c3 = d7
					} else {
						if (c3 < d7) {
							dI.pop()
						}
					}
					if (!bh) {
						bh = {
							x: 0,
							y: 0
						}
					}
					if (!dO && dI.length > bh[ds]) {
						bh[ds] = dI.length
					}
				}
			}
			function dY() {
				if (bh && !dO && !dB && !c2) {
					var d0 = cP,
						dZ = dI.length;
					cP = bh[ds];
					if (dZ < cP) {
						while (dI.length < cP) {
							dI.push(dk(dI[dI.length - 1] + cV))
						}
						du *= (dZ - 1) / (cP - 1);
						c3 = dI[dI.length - 1]
					}
					if (j(d0) && cP !== d0) {
						cT.isDirty = true
					}
				}
			}
			function dp() {
				var d0, dZ;
				dm = dS;
				dC = c3;
				cM();
				dh();
				dc = du;
				du = c0 / ((c3 - dS) || 1);
				if (!dQ) {
					for (d0 in dl) {
						for (dZ in dl[d0]) {
							dl[d0][dZ].cum = dl[d0][dZ].total
						}
					}
				}
				if (!cT.isDirty) {
					cT.isDirty = (dS !== dm || c3 !== dC)
				}
			}
			function dw(d1, dZ, d2, d0) {
				d2 = b(d2, true);
				fireEvent(cT, "setExtremes", {
					min: d1,
					max: dZ
				}, function() {
					cJ = d1;
					c6 = dZ;
					if (d2) {
						ct.redraw(d0)
					}
				})
			}
			function cL() {
				return {
					min: dS,
					max: c3,
					dataMin: dP,
					dataMax: c1,
					userMin: cJ,
					userMax: c6
				}
			}
			function dr(dZ) {
				if (dS > dZ) {
					dZ = dS
				} else {
					if (c3 < dZ) {
						dZ = c3
					}
				}
				return cZ(dZ, 0, 1)
			}
			function cY(dZ) {
				var d0 = new dx(dZ).render();
				cU.push(d0);
				return d0
			}
			function dH() {
				var d1 = dG.length && j(dS) && j(c3),
					d4 = 0,
					d3 = 0,
					d2 = dn.title,
					d0 = dn.labels,
					dZ = [-1, 1, 1, -1][dR],
					d5;
				if (!c4) {
					c4 = b0.g("axis").attr({
						zIndex: 7
					}).add();
					dK = b0.g("grid").attr({
						zIndex: 1
					}).add()
				}
				dT = 0;
				if (d1 || c2) {
					each(dI, function(d6) {
						if (!dD[d6]) {
							dD[d6] = new c8(d6)
						} else {
							dD[d6].addLabel()
						}
						if (dR === 0 || dR === 2 || {
							1: "left",
							3: "right"
						}[dR] === d0.align) {
							dT = aQ(dD[d6].getLabelSize(), dT)
						}
					});
					if (dq) {
						dT += (dq - 1) * 16
					}
				} else {
					for (d5 in dD) {
						dD[d5].destroy();
						delete dD[d5]
					}
				}
				if (d2 && d2.text) {
					if (!dN) {
						dN = cT.axisTitle = b0.text(d2.text, 0, 0, d2.useHTML).attr({
							zIndex: 7,
							rotation: d2.rotation || 0,
							align: d2.textAlign || {
								low: "left",
								middle: "center",
								high: "right"
							}[d2.align]
						}).css(d2.style).add();
						dN.isNew = true
					}
					d4 = dN.getBBox()[cN ? "height" : "width"];
					d3 = b(d2.margin, cN ? 5 : 10)
				}
				cX = dZ * (dn.offset || br[dR]);
				dj = dT + (dR !== 2 && dT && dZ * dn.labels[cN ? "y" : "x"]) + d3;
				br[dR] = aQ(br[dR], dj + d4 + dZ * cX)
			}
			function dg() {
				var d2 = dn.title,
					ee = dn.stackLabels,
					d1 = dn.alternateGridColor,
					d0 = dn.lineWidth,
					eh, d5, d8, eb = ct.hasRendered,
					ea = eb && j(dm) && !isNaN(dm),
					eg = dG.length && j(dS) && j(c3);
				c0 = cN ? be : bo;
				du = c0 / ((c3 - dS) || 1);
				dt = cN ? M : bU;
				if (eg || c2) {
					if (dv && !dB) {
						var d7 = dS + (dI[0] - dS) % dv;
						for (; d7 <= c3; d7 += dv) {
							if (!dX[d7]) {
								dX[d7] = new c8(d7, true)
							}
							if (ea && dX[d7].isNew) {
								dX[d7].render(null, true)
							}
							dX[d7].isActive = true;
							dX[d7].render()
						}
					}
					each(dI, function(ej, ei) {
						if (!c2 || (ej >= dS && ej <= c3)) {
							if (ea && dD[ej].isNew) {
								dD[ej].render(ei, true)
							}
							dD[ej].isActive = true;
							dD[ej].render(ei)
						}
					});
					if (d1) {
						each(dI, function(ej, ei) {
							if (ei % 2 === 0 && ej < c3) {
								if (!cW[ej]) {
									cW[ej] = new dx()
								}
								cW[ej].options = {
									from: ej,
									to: dI[ei + 1] !== aA ? dI[ei + 1] : c3,
									color: d1
								};
								cW[ej].render();
								cW[ej].isActive = true
							}
						})
					}
					if (!eb) {
						each((dn.plotLines || []).concat(dn.plotBands || []), function(ei) {
							cU.push(new dx(ei).render())
						})
					}
				}
				each([dD, dX, cW], function(ei) {
					var ej;
					for (ej in ei) {
						if (!ei[ej].isActive) {
							ei[ej].destroy();
							delete ei[ej]
						} else {
							ei[ej].isActive = false
						}
					}
				});
				if (d0) {
					eh = M + (dy ? be : 0) + cX;
					d5 = bw - bU - (dy ? bo : 0) + cX;
					d8 = b0.crispLine([au, cN ? M : eh, cN ? d5 : ci, av, cN ? cG - co : eh, cN ? d5 : bw - bU], d0);
					if (!dM) {
						dM = b0.path(d8).attr({
							stroke: dn.lineColor,
							"stroke-width": d0,
							zIndex: 7
						}).add()
					} else {
						dM.animate({
							d: d8
						})
					}
				}
				if (dN) {
					var ec = cN ? M : ci,
						d4 = p(d2.style.fontSize || 12),
						d9 = {
							low: ec + (cN ? 0 : c0),
							middle: ec + c0 / 2,
							high: ec + (cN ? c0 : 0)
						}[d2.align],
						ed = (cN ? ci + bo : M) + (cN ? 1 : -1) * (dy ? -1 : 1) * dj + (dR === 2 ? d4 : 0);
					dN[dN.isNew ? "attr" : "animate"]({
						x: cN ? d9 : ed + (dy ? be : 0) + cX + (d2.x || 0),
						y: cN ? ed - (dy ? bo : 0) + cX : d9 + (d2.y || 0)
					});
					dN.isNew = false
				}
				if (ee && ee.enabled) {
					var d3, d6, ef, dZ = cT.stackTotalGroup;
					if (!dZ) {
						cT.stackTotalGroup = dZ = b0.g("stack-labels").attr({
							visibility: al,
							zIndex: 6
						}).translate(M, ci).add()
					}
					for (d3 in dl) {
						d6 = dl[d3];
						for (ef in d6) {
							d6[ef].render(dZ)
						}
					}
				}
				cT.isDirty = false
			}
			function cI(d0) {
				var dZ = cU.length;
				while (dZ--) {
					if (cU[dZ].id === d0) {
						cU[dZ].destroy()
					}
				}
			}
			function df() {
				if (L.resetTracker) {
					L.resetTracker()
				}
				dg();
				each(cU, function(dZ) {
					dZ.render()
				});
				each(dG, function(dZ) {
					dZ.isDirty = true
				})
			}
			function de(dZ, d0) {
				cT.categories = dF.categories = dB = dZ;
				each(dG, function(d1) {
					d1.translate();
					d1.setTooltipPoints(true)
				});
				cT.isDirty = true;
				if (b(d0, true)) {
					ct.redraw()
				}
			}
			function cQ() {
				var dZ;
				removeEvent(cT);
				for (dZ in dl) {
					a9(dl[dZ]);
					dl[dZ] = null
				}
				if (cT.stackTotalGroup) {
					cT.stackTotalGroup = cT.stackTotalGroup.destroy()
				}
				each([dD, dX, cW, cU], function(d0) {
					a9(d0)
				});
				each([dM, c4, dK, dN], function(d0) {
					if (d0) {
						d0.destroy()
					}
				});
				dM = c4 = dK = dN = null
			}
			if (bl && dQ && cO === aA) {
				cO = true
			}
			aw(cT, {
				addPlotBand: cY,
				addPlotLine: cY,
				adjustTickAmount: dY,
				categories: dB,
				getExtremes: cL,
				getPlotLinePath: cS,
				getThreshold: dr,
				isXAxis: dQ,
				options: dn,
				plotLinesAndBands: cU,
				getOffset: dH,
				render: dg,
				setCategories: de,
				setExtremes: dw,
				setScale: dp,
				setTickPositions: dh,
				translate: cZ,
				redraw: df,
				removePlotBand: cI,
				removePlotLine: cI,
				reversed: cO,
				stacks: dl,
				destroy: cQ
			});
			for (db in c7) {
				addEvent(cT, db, c7[db])
			}
			dp()
		}
		function cl() {
			var cJ = {};

			function cK(cP, cO, cN, cM) {
				if (!cJ[cP]) {
					var cL = b0.text(cO, 0, 0).css(bZ.toolbar.itemStyle).align({
						align: "right",
						x: -co - 20,
						y: ci + 30
					}).on("click", cM).attr({
						align: "right",
						zIndex: 20
					}).add();
					cJ[cP] = cL
				}
			}
			function cI(cL) {
				U(cJ[cL].element);
				cJ[cL] = null
			}
			return {
				add: cK,
				remove: cI
			}
		}
		function cw(cK) {
			var cT, c3 = cK.borderWidth,
				cI = cK.crosshairs,
				cV = [],
				c1 = cK.style,
				cJ = cK.shared,
				cW = p(c1.padding),
				cX = c3 + cW,
				cU = true,
				cP, cM, c0 = 0,
				cZ = 0;
			c1.padding = 0;
			var cN = b0.g("tooltip").attr({
				zIndex: 8
			}).add(),
				cQ = b0.rect(cX, cX, 0, 0, cK.borderRadius, c3).attr({
					fill: cK.backgroundColor,
					"stroke-width": c3
				}).add(cN).shadow(cK.shadow),
				cO = b0.text("", cW + cX, p(c1.fontSize) + cW + cX, cK.useHTML).attr({
					zIndex: 1
				}).css(c1).add(cN);
			cN.hide();

			function c2() {
				each(cV, function(c4) {
					if (c4) {
						c4.destroy()
					}
				});
				each([cQ, cO, cN], function(c4) {
					if (c4) {
						c4.destroy()
					}
				});
				cQ = cO = cN = null
			}
			function cS() {
				var da = this,
					c5 = da.points || af(da),
					c9 = c5[0].series.xAxis,
					c4 = da.x,
					c8 = c9 && c9.options.type === "datetime",
					c7 = a1(c4) || c8,
					c6;
				c6 = c7 ? ['<span style="font-size: 10px">' + (c8 ? aB("%A, %b %e, %Y", c4) : c4) + "</span>"] : [];
				each(c5, function(db) {
					c6.push(db.point.tooltipFormatter(c7))
				});
				return c6.join("<br/>")
			}
			function cY(c5, c4) {
				c0 = cU ? c5 : (2 * c0 + c5) / 3;
				cZ = cU ? c4 : (cZ + c4) / 2;
				cN.translate(c0, cZ);
				if (ba(c5 - c0) > 1 || ba(c4 - cZ) > 1) {
					cg = function() {
						cY(c5, c4)
					}
				} else {
					cg = null
				}
			}
			function cR() {
				if (!cU) {
					var c4 = ct.hoverPoints;
					cN.hide();
					each(cV, function(c5) {
						if (c5) {
							c5.hide()
						}
					});
					if (c4) {
						each(c4, function(c5) {
							c5.setState()
						})
					}
					ct.hoverPoints = null;
					cU = true
				}
			}
			function cL(di) {
				var dd, dc, dl, da, c8, c7 = 0,
					db = {},
					df, c9 = [],
					c4 = di.tooltipPos,
					dj = cK.formatter || cS,
					de = ct.hoverPoints,
					c6;
				if (cJ) {
					if (de) {
						each(de, function(dm) {
							dm.setState()
						})
					}
					ct.hoverPoints = di;
					each(di, function(dm) {
						dm.setState(S);
						c7 += dm.plotY;
						c9.push(dm.getLabelConfig())
					});
					c8 = di[0].plotX;
					c7 = u(c7) / di.length;
					db = {
						x: di[0].category
					};
					db.points = c9;
					di = di[0]
				} else {
					db = di.getLabelConfig()
				}
				df = dj.call(db);
				cT = di.series;
				c8 = cJ ? c8 : di.plotX;
				c7 = cJ ? c7 : di.plotY;
				dd = u(c4 ? c4[0] : (bl ? be - c7 : c8));
				dc = u(c4 ? c4[1] : (bl ? bo - c8 : c7));
				dl = cJ || !di.series.isCartesian || by(dd, dc);
				if (df === false || !dl) {
					cR()
				} else {
					if (cU) {
						cN.show();
						cU = false
					}
					cO.attr({
						text: df
					});
					da = cO.getBBox();
					cP = da.width + 2 * cW;
					cM = da.height + 2 * cW;
					cQ.attr({
						width: cP,
						height: cM,
						stroke: cK.borderColor || di.color || cT.color || "#606060"
					});
					c6 = X(cP, cM, M, ci, be, bo, {
						x: dd,
						y: dc
					});
					cY(u(c6.x - cX), u(c6.y - cX))
				}
				if (cI) {
					cI = af(cI);
					var dh, dk = cI.length,
						dg, c5;
					while (dk--) {
						c5 = di.series[dk ? "yAxis" : "xAxis"];
						if (cI[dk] && c5) {
							dh = c5.getPlotLinePath(di[dk ? "y" : "x"], 1);
							if (cV[dk]) {
								cV[dk].attr({
									d: dh,
									visibility: al
								})
							} else {
								dg = {
									"stroke-width": cI[dk].width || 1,
									stroke: cI[dk].color || "#C0C0C0",
									zIndex: 2
								};
								if (cI[dk].dashStyle) {
									dg.dashstyle = cI[dk].dashStyle
								}
								cV[dk] = b0.path(dh).attr(dg).add()
							}
						}
					}
				}
			}
			return {
				shared: cJ,
				refresh: cL,
				hide: cR,
				destroy: c2
			}
		}
		function cs(cK) {
			var cY, cX, cV, cI, cU = bE.zoomType,
				cT = /x/.test(cU),
				cS = /y/.test(cU),
				cW = (cT && !bl) || (cS && bl),
				cJ = (cS && !bl) || (cT && bl);

			function cN(c3) {
				var c1, c6 = az && I.width / I.body.scrollWidth - 1,
					c2, c5, c0, c4;
				c3 = c3 || aD.event;
				if (!c3.target) {
					c3.target = c3.srcElement
				}
				c1 = c3.touches ? c3.touches.item(0) : c3;
				if (c3.type !== "mousemove" || aD.opera || c6) {
					b5 = s(cc);
					c2 = b5.left;
					c5 = b5.top
				}
				if (a2) {
					c0 = c3.x;
					c4 = c3.y
				} else {
					if (c1.layerX === aA) {
						c0 = c1.pageX - c2;
						c4 = c1.pageY - c5
					} else {
						c0 = c3.layerX;
						c4 = c3.layerY
					}
				}
				if (c6) {
					c0 += u((c6 + 1) * c2 - c2);
					c4 += u((c6 + 1) * c5 - c5)
				}
				return aw(c3, {
					chartX: c0,
					chartY: c4
				})
			}
			function cM(c0) {
				var c1 = {
					xAxis: [],
					yAxis: []
				};
				each(ck, function(c2) {
					var c5 = c2.translate,
						c4 = c2.isXAxis,
						c3 = bl ? !c4 : c4;
					c1[c4 ? "xAxis" : "yAxis"].push({
						axis: c2,
						value: c5(c3 ? c0.chartX - M : bo - c0.chartY + ci, true)
					})
				});
				return c1
			}
			function cL(c5) {
				var c7, c8, c0 = ct.hoverPoint,
					c6 = ct.hoverSeries,
					c3, c2, c1 = cG,
					c4 = bl ? c5.chartY : c5.chartX - M;
				if (bp && cK.shared) {
					c8 = [];
					c3 = b3.length;
					for (c2 = 0; c2 < c3; c2++) {
						if (b3[c2].visible && b3[c2].tooltipPoints.length) {
							c7 = b3[c2].tooltipPoints[c4];
							c7._dist = ba(c4 - c7.plotX);
							c1 = ad(c1, c7._dist);
							c8.push(c7)
						}
					}
					c3 = c8.length;
					while (c3--) {
						if (c8[c3]._dist > c1) {
							c8.splice(c3, 1)
						}
					}
					if (c8.length && (c8[0].plotX !== bt)) {
						bp.refresh(c8);
						bt = c8[0].plotX
					}
				}
				if (c6 && c6.tracker) {
					c7 = c6.tooltipPoints[c4];
					if (c7 && c7 !== c0) {
						c7.onMouseOver()
					}
				}
			}
			function cR() {
				var c1 = ct.hoverSeries,
					c0 = ct.hoverPoint;
				if (c0) {
					c0.onMouseOut()
				}
				if (c1) {
					c1.onMouseOut()
				}
				if (bp) {
					bp.hide()
				}
				bt = null
			}
			function cQ() {
				if (cI) {
					var c2 = {
						xAxis: [],
						yAxis: []
					},
						c0 = cI.getBBox(),
						c3 = c0.x - M,
						c1 = c0.y - ci;
					if (cV) {
						each(ck, function(c5) {
							var c9 = c5.translate,
								c8 = c5.isXAxis,
								c7 = bl ? !c8 : c8,
								c4 = c9(c7 ? c3 : bo - c1 - c0.height, true, 0, 0, 1),
								c6 = c9(c7 ? c3 + c0.width : bo - c1, true, 0, 0, 1);
							c2[c8 ? "xAxis" : "yAxis"].push({
								axis: c5,
								min: ad(c4, c6),
								max: aQ(c4, c6)
							})
						});
						fireEvent(ct, "selection", c2, bi)
					}
					cI = cI.destroy()
				}
				ct.mouseIsDown = bF = cV = false;
				removeEvent(I, aZ ? "touchend" : "mouseup", cQ)
			}
			function cP(c2) {
				var c1 = j(c2.pageX) ? c2.pageX : c2.page.x,
					c0 = j(c2.pageX) ? c2.pageY : c2.page.y;
				if (b5 && !by(c1 - b5.left - M, c0 - b5.top - ci)) {
					cR()
				}
			}
			function cO() {
				var c1 = true;
				cc.onmousedown = function(c2) {
					c2 = cN(c2);
					if (!aZ && c2.preventDefault) {
						c2.preventDefault()
					}
					ct.mouseIsDown = bF = true;
					cY = c2.chartX;
					cX = c2.chartY;
					addEvent(I, aZ ? "touchend" : "mouseup", cQ)
				};
				var c0 = function(c6) {
						if (c6 && c6.touches && c6.touches.length > 1) {
							return
						}
						c6 = cN(c6);
						if (!aZ) {
							c6.returnValue = false
						}
						var c2 = c6.chartX,
							c7 = c6.chartY,
							c4 = !by(c2 - M, c7 - ci);
						if (!b5) {
							b5 = s(cc)
						}
						if (aZ && c6.type === "touchstart") {
							if (ac(c6.target, "isTracker")) {
								if (!ct.runTrackerClick) {
									c6.preventDefault()
								}
							} else {
								if (!cH && !c4) {
									c6.preventDefault()
								}
							}
						}
						if (c4) {
							if (c2 < M) {
								c2 = M
							} else {
								if (c2 > M + be) {
									c2 = M + be
								}
							}
							if (c7 < ci) {
								c7 = ci
							} else {
								if (c7 > ci + bo) {
									c7 = ci + bo
								}
							}
						}
						if (bF && c6.type !== "touchstart") {
							cV = Math.sqrt(Math.pow(cY - c2, 2) + Math.pow(cX - c7, 2));
							if (cV > 10) {
								if (b7 && (cT || cS) && by(cY - M, cX - ci)) {
									if (!cI) {
										cI = b0.rect(M, ci, cW ? 1 : be, cJ ? 1 : bo, 0).attr({
											fill: bE.selectionMarkerFill || "rgba(69,114,167,0.25)",
											zIndex: 7
										}).add()
									}
								}
								if (cI && cW) {
									var c5 = c2 - cY;
									cI.attr({
										width: ba(c5),
										x: (c5 > 0 ? 0 : c5) + cY
									})
								}
								if (cI && cJ) {
									var c3 = c7 - cX;
									cI.attr({
										height: ba(c3),
										y: (c3 > 0 ? 0 : c3) + cX
									})
								}
							}
						} else {
							if (!c4) {
								cL(c6)
							}
						}
						c1 = c4;
						return c4 || !b7
					};
				cc.onmousemove = c0;
				addEvent(cc, "mouseleave", cR);
				addEvent(I, "mousemove", cP);
				cc.ontouchstart = function(c2) {
					if (cT || cS) {
						cc.onmousedown(c2)
					}
					c0(c2)
				};
				cc.ontouchmove = c0;
				cc.ontouchend = function() {
					if (cV) {
						cR()
					}
				};
				cc.onclick = function(c5) {
					var c3 = ct.hoverPoint;
					c5 = cN(c5);
					c5.cancelBubble = true;
					if (!cV) {
						if (c3 && ac(c5.target, "isTracker")) {
							var c4 = c3.plotX,
								c2 = c3.plotY;
							aw(c3, {
								pageX: b5.left + M + (bl ? be - c2 : c4),
								pageY: b5.top + ci + (bl ? bo - c4 : c2)
							});
							fireEvent(c3.series, "click", aw(c5, {
								point: c3
							}));
							c3.firePointEvent("click", c5)
						} else {
							aw(c5, cM(c5));
							if (by(c5.chartX - M, c5.chartY - ci)) {
								fireEvent(ct, "click", c5)
							}
						}
					}
					cV = false
				}
			}
			function cZ() {
				if (ct.trackerGroup) {
					ct.trackerGroup = b9 = ct.trackerGroup.destroy()
				}
				removeEvent(I, "mousemove", cP);
				cc.onclick = cc.onmousedown = cc.onmousemove = cc.ontouchstart = cc.ontouchend = cc.ontouchmove = null
			}
			bI = function() {
				if (!b9) {
					ct.trackerGroup = b9 = b0.g("tracker").attr({
						zIndex: 9
					}).add()
				} else {
					b9.translate(M, ci);
					if (bl) {
						b9.attr({
							width: ct.plotWidth,
							height: ct.plotHeight
						}).invert()
					}
				}
			};
			bI();
			if (cK.enabled) {
				ct.tooltip = bp = cw(cK)
			}
			cO();
			b8 = setInterval(function() {
				if (cg) {
					cg()
				}
			}, 32);
			aw(this, {
				zoomX: cT,
				zoomY: cS,
				resetTracker: cR,
				destroy: cZ
			})
		}
		var cx = function() {
				var cN = ct.options.legend;
				if (!cN.enabled) {
					return
				}
				var c9 = cN.layout === "horizontal",
					da = cN.symbolWidth,
					cI = cN.symbolPadding,
					c1, c8 = cN.style,
					cQ = cN.itemStyle,
					cR = cN.itemHoverStyle,
					cK = cN.itemHiddenStyle,
					c0 = p(c8.padding),
					cU = 18,
					c6 = 4 + c0 + da + cI,
					cL, cJ, c2, c4 = 0,
					cW, cZ = cN.borderWidth,
					cS = cN.backgroundColor,
					cO, c3, cY = cN.width,
					cV = ct.series,
					c5 = cN.reversed;

				function dc(dl, de) {
					var df = dl.legendItem,
						dg = dl.legendLine,
						dd = dl.legendSymbol,
						dj = cK.color,
						di = de ? cN.itemStyle.color : dj,
						dh = de ? dl.color : dj,
						dk = de ? dl.pointAttr[Y] : {
							stroke: dj,
							fill: dj
						};
					if (df) {
						df.css({
							fill: di
						})
					}
					if (dg) {
						dg.attr({
							stroke: dh
						})
					}
					if (dd) {
						dd.attr(dk)
					}
				}
				function c7(df, di, de) {
					var dd = df.legendItem,
						dh = df.legendLine,
						dj = df.legendSymbol,
						dg = df.checkbox;
					if (dd) {
						dd.attr({
							x: di,
							y: de
						})
					}
					if (dh) {
						dh.translate(di, de - 4)
					}
					if (dj) {
						dj.attr({
							x: di + dj.xOff,
							y: de + dj.yOff
						})
					}
					if (dg) {
						dg.x = di;
						dg.y = de
					}
				}
				function cP(dd) {
					var de = dd.checkbox;
					each(["legendItem", "legendLine", "legendSymbol"], function(df) {
						if (dd[df]) {
							dd[df].destroy()
						}
					});
					if (de) {
						U(dd.checkbox)
					}
				}
				function db() {
					if (cW) {
						cW = cW.destroy()
					}
					if (cO) {
						cO = cO.destroy()
					}
				}
				function cT() {
					each(c1, function(dd) {
						var de = dd.checkbox,
							df = cO.alignAttr;
						if (de) {
							ax(de, {
								left: (df.translateX + dd.legendItemWidth + de.x - 40) + aj,
								top: (df.translateY + de.y - 11) + aj
							})
						}
					})
				}
				function cX(dm) {
					var dn, di, dd, df, de, dh, dl = dm.legendItem,
						dg = dm.series || dm,
						dp = dg.options,
						dj = (dp && dp.borderWidth) || 0;
					if (!dl) {
						dh = /^(bar|pie|area|column)$/.test(dg.type);
						dm.legendItem = dl = b0.text(cN.labelFormatter.call(dm), 0, 0).css(dm.visible ? cQ : cK).on("mouseover", function() {
							dm.setState(S);
							dl.css(cR)
						}).on("mouseout", function() {
							dl.css(dm.visible ? cQ : cK);
							dm.setState()
						}).on("click", function() {
							var dr = "legendItemClick",
								dq = function() {
									dm.setVisible()
								};
							if (dm.firePointEvent) {
								dm.firePointEvent(dr, null, dq)
							} else {
								fireEvent(dm, dr, null, dq)
							}
						}).attr({
							zIndex: 2
						}).add(cO);
						if (!dh && dp && dp.lineWidth) {
							var dk = {
								"stroke-width": dp.lineWidth,
								zIndex: 2
							};
							if (dp.dashStyle) {
								dk.dashstyle = dp.dashStyle
							}
							dm.legendLine = b0.path([au, -da - cI, 0, av, -cI, 0]).attr(dk).add(cO)
						}
						if (dh) {
							dd = b0.rect((df = -da - cI), (de = -11), da, 12, 2).attr({
								zIndex: 3
							}).add(cO)
						} else {
							if (dp && dp.marker && dp.marker.enabled) {
								dd = b0.symbol(dm.symbol, (df = -da / 2 - cI), (de = -4), dp.marker.radius).attr({
									zIndex: 3
								}).add(cO)
							}
						}
						if (dd) {
							dd.xOff = df + (dj % 2 / 2);
							dd.yOff = de + (dj % 2 / 2)
						}
						dm.legendSymbol = dd;
						dc(dm, dm.visible);
						if (dp && dp.showCheckbox) {
							dm.checkbox = ar("input", {
								type: "checkbox",
								checked: dm.selected,
								defaultChecked: dm.selected
							}, cN.itemCheckboxStyle, cc);
							addEvent(dm.checkbox, "click", function(dq) {
								var dr = dq.target;
								fireEvent(dm, "checkboxClick", {
									checked: dr.checked
								}, function() {
									dm.select()
								})
							})
						}
					}
					dn = dl.getBBox();
					di = dm.legendItemWidth = cN.itemWidth || da + cI + dn.width + c0;
					c4 = dn.height;
					if (c9 && cL - c6 + di > (cY || (cG - 2 * c0 - c6))) {
						cL = c6;
						cJ += c4
					}
					c2 = cJ;
					c7(dm, cL, cJ);
					if (c9) {
						cL += di
					} else {
						cJ += c4
					}
					c3 = cY || aQ(c9 ? cL - c6 : di, c3)
				}
				function cM() {
					cL = c6;
					cJ = cU;
					c3 = 0;
					c2 = 0;
					if (!cO) {
						cO = b0.g("legend").attr({
							zIndex: 7
						}).add()
					}
					c1 = [];
					each(cV, function(dh) {
						var dg = dh.options;
						if (!dg.showInLegend) {
							return
						}
						c1 = c1.concat(dg.legendType === "point" ? dh.data : dh)
					});
					K(c1, function(dh, dg) {
						return (dh.options.legendIndex || 0) - (dg.options.legendIndex || 0)
					});
					if (c5) {
						c1.reverse()
					}
					each(c1, cX);
					bu = cY || c3;
					bm = c2 - cU + c4;
					if (cZ || cS) {
						bu += 2 * c0;
						bm += 2 * c0;
						if (!cW) {
							cW = b0.rect(0, 0, bu, bm, cN.borderRadius, cZ || 0).attr({
								stroke: cN.borderColor,
								"stroke-width": cZ || 0,
								fill: cS || ah
							}).add(cO).shadow(cN.shadow);
							cW.isNew = true
						} else {
							if (bu > 0 && bm > 0) {
								cW[cW.isNew ? "attr" : "animate"](cW.crisp(null, null, null, bu, bm));
								cW.isNew = false
							}
						}
						cW[c1.length ? "show" : "hide"]()
					}
					var de = ["left", "right", "top", "bottom"],
						df, dd = 4;
					while (dd--) {
						df = de[dd];
						if (c8[df] && c8[df] !== "auto") {
							cN[dd < 2 ? "align" : "verticalAlign"] = df;
							cN[dd < 2 ? "x" : "y"] = p(c8[df]) * (dd % 2 ? -1 : 1)
						}
					}
					if (c1.length) {
						cO.align(aw(cN, {
							width: bu,
							height: bm
						}), true, bT)
					}
					if (!bC) {
						cT()
					}
				}
				cM();
				addEvent(ct, "endResize", cT);
				return {
					colorizeItem: dc,
					destroyItem: cP,
					renderLegend: cM,
					destroy: db
				}
			};

		function cA(cI) {
			var cJ = cI.type || bE.type || bE.defaultSeriesType,
				cM = a6[cJ],
				cK, cL = ct.hasRendered;
			if (cL) {
				if (bl && cJ === "column") {
					cM = a6.bar
				} else {
					if (!bl && cJ === "bar") {
						cM = a6.column
					}
				}
			}
			cK = new cM();
			cK.init(ct, cI);
			if (!cL && cK.inverted) {
				bl = true
			}
			if (cK.isCartesian) {
				b7 = cK.isCartesian
			}
			b3.push(cK);
			return cK
		}
		function cz(cI, cL, cK) {
			var cJ;
			if (cI) {
				ay(cK, ct);
				cL = b(cL, true);
				fireEvent(ct, "addSeries", {
					options: cI
				}, function() {
					cJ = cA(cI);
					cJ.isDirty = true;
					ct.isDirtyLegend = true;
					if (cL) {
						ct.redraw()
					}
				})
			}
			return cJ
		}
		by = function(cI, cJ) {
			return cI >= 0 && cI <= be && cJ >= 0 && cJ <= bo
		};

		function b4() {
			if (bE.alignTicks !== false) {
				each(ck, function(cI) {
					cI.adjustTickAmount()
				})
			}
			bh = null
		}
		function bM(cP) {
			var cK = ct.isDirtyLegend,
				cJ, cO = ct.isDirtyBox,
				cI = b3.length,
				cM = cI,
				cL = ct.clipRect,
				cN;
			ay(cP, ct);
			while (cM--) {
				cN = b3[cM];
				if (cN.isDirty && cN.options.stacking) {
					cJ = true;
					break
				}
			}
			if (cJ) {
				cM = cI;
				while (cM--) {
					cN = b3[cM];
					if (cN.options.stacking) {
						cN.isDirty = true
					}
				}
			}
			each(b3, function(cQ) {
				if (cQ.isDirty) {
					cQ.cleanData();
					cQ.getSegments();
					if (cQ.options.legendType === "point") {
						cK = true
					}
				}
			});
			if (cK && cB.renderLegend) {
				cB.renderLegend();
				ct.isDirtyLegend = false
			}
			if (b7) {
				if (!bC) {
					bh = null;
					each(ck, function(cQ) {
						cQ.setScale()
					})
				}
				b4();
				cp();
				each(ck, function(cQ) {
					if (cQ.isDirty || cO) {
						cQ.redraw();
						cO = true
					}
				})
			}
			if (cO) {
				b6();
				bI();
				if (cL) {
					stop(cL);
					cL.animate({
						width: ct.plotSizeX,
						height: ct.plotSizeY
					})
				}
			}
			each(b3, function(cQ) {
				if (cQ.isDirty && cQ.visible && (!cQ.isCartesian || cQ.xAxis)) {
					cQ.redraw()
				}
			});
			if (L && L.resetTracker) {
				L.resetTracker()
			}
			fireEvent(ct, "redraw")
		}
		function bH(cJ) {
			var cI = bZ.loading;
			if (!bg) {
				bg = ar(an, {
					className: "charts-loading"
				}, aw(cI.style, {
					left: M + aj,
					top: ci + aj,
					width: be + aj,
					height: bo + aj,
					zIndex: 10,
					display: ah
				}), cc);
				cr = ar("span", null, cI.labelStyle, bg)
			}
			cr.innerHTML = cJ || bZ.lang.loading;
			if (!cE) {
				ax(bg, {
					opacity: 0,
					display: ""
				});
				animate(bg, {
					opacity: cI.style.opacity
				}, {
					duration: cI.showDuration
				});
				cE = true
			}
		}
		function cy() {
			animate(bg, {
				opacity: 0
			}, {
				duration: bZ.loading.hideDuration,
				complete: function() {
					ax(bg, {
						display: ah
					})
				}
			});
			cE = false
		}
		function ch(cL) {
			var cJ, cI, cK;
			for (cJ = 0; cJ < ck.length; cJ++) {
				if (ck[cJ].options.id === cL) {
					return ck[cJ]
				}
			}
			for (cJ = 0; cJ < b3.length; cJ++) {
				if (b3[cJ].options.id === cL) {
					return b3[cJ]
				}
			}
			for (cJ = 0; cJ < b3.length; cJ++) {
				cK = b3[cJ].data;
				for (cI = 0; cI < cK.length; cI++) {
					if (cK[cI].id === cL) {
						return cK[cI]
					}
				}
			}
			return null
		}
		function bQ() {
			var cI = bZ.xAxis || {},
				cK = bZ.yAxis || {},
				cJ;
			cI = af(cI);
			each(cI, function(cM, cL) {
				cM.index = cL;
				cM.isX = true
			});
			cK = af(cK);
			each(cK, function(cM, cL) {
				cM.index = cL
			});
			ck = cI.concat(cK);
			ct.xAxis = [];
			ct.yAxis = [];
			ck = map(ck, function(cL) {
				cJ = new bv(cL);
				ct[cJ.isXAxis ? "xAxis" : "yAxis"].push(cJ);
				return cJ
			});
			b4()
		}
		function b1() {
			var cI = [];
			each(b3, function(cJ) {
				cI = cI.concat(grep(cJ.data, function(cK) {
					return cK.selected
				}))
			});
			return cI
		}
		function cm() {
			return grep(b3, function(cI) {
				return cI.selected
			})
		}
		bN = function() {
			fireEvent(ct, "selection", {
				resetSelection: true
			}, bi);
			ct.toolbar.remove("zoom")
		};
		bi = function(cJ) {
			var cK = aK.lang,
				cI = ct.pointCount < 100;
			ct.toolbar.add("zoom", cK.resetZoom, cK.resetZoomTitle, bN);
			if (!cJ || cJ.resetSelection) {
				each(ck, function(cL) {
					cL.setExtremes(null, null, false, cI)
				})
			} else {
				each(cJ.xAxis.concat(cJ.yAxis), function(cL) {
					var cM = cL.axis;
					if (ct.tracker[cM.isXAxis ? "zoomX" : "zoomY"]) {
						cM.setExtremes(cL.min, cL.max, false, cI)
					}
				})
			}
			bM()
		};

		function cD(cJ, cI) {
			bW = merge(bZ.title, cJ);
			ce = merge(bZ.subtitle, cI);
			each([
				["title", cJ, bW],
				["subtitle", cI, ce]
			], function(cK) {
				var cL = cK[0],
					cO = ct[cL],
					cN = cK[1],
					cM = cK[2];
				if (cO && cN) {
					cO = cO.destroy()
				}
				if (cM && cM.text && !cO) {
					ct[cL] = b0.text(cM.text, 0, 0, cM.useHTML).attr({
						align: cM.align,
						"class": "charts-" + cL,
						zIndex: 1
					}).css(cM.style).add().align(cM, false, bT)
				}
			})
		}
		function cC() {
			cv = (cj || bK).offsetWidth;
			bR = (cj || bK).offsetHeight;
			ct.chartWidth = cG = bE.width || cv || 600;
			ct.chartHeight = bw = bE.height || (bR > 19 ? bR : 400)
		}
		function bY() {
			bK = bE.renderTo;
			bO = z + Q++;
			if (a1(bK)) {
				bK = I.getElementById(bK)
			}
			bK.innerHTML = "";
			if (!bK.offsetWidth) {
				cj = bK.cloneNode(0);
				ax(cj, {
					position: a0,
					top: "-9999px",
					display: ""
				});
				I.body.appendChild(cj)
			}
			cC();
			ct.container = cc = ar(an, {
				className: "charts-container" + (bE.className ? " " + bE.className : ""),
				id: bO
			}, aw({
				position: m,
				overflow: at,
				width: cG + aj,
				height: bw + aj,
				textAlign: "left"
			}, bE.style), cj || bK);
			ct.renderer = b0 = bE.forExport ? new q(cc, cG, bw, true) : new aG(cc, cG, bw);
			var cI, cJ;
			if (f && cc.getBoundingClientRect) {
				cI = function() {
					ax(cc, {
						left: 0,
						top: 0
					});
					cJ = cc.getBoundingClientRect();
					ax(cc, {
						left: (-(cJ.left - p(cJ.left))) + aj,
						top: (-(cJ.top - p(cJ.top))) + aj
					})
				};
				cI();
				addEvent(aD, "resize", cI);
				addEvent(ct, "destroy", function() {
					removeEvent(aD, "resize", cI)
				})
			}
		}
		cp = function() {
			var cI = bZ.legend,
				cM = b(cI.margin, 10),
				cK = cI.x,
				cJ = cI.y,
				cO = cI.align,
				cN = cI.verticalAlign,
				cL;
			bq();
			if ((ct.title || ct.subtitle) && !j(bB)) {
				cL = aQ((ct.title && !bW.floating && !bW.verticalAlign && bW.y) || 0, (ct.subtitle && !ce.floating && !ce.verticalAlign && ce.y) || 0);
				if (cL) {
					ci = aQ(ci, cL + b(bW.margin, 15) + cF)
				}
			}
			if (cI.enabled && !cI.floating) {
				if (cO === "right") {
					if (!j(bz)) {
						co = aQ(co, bu - cK + cM + bL)
					}
				} else {
					if (cO === "left") {
						if (!j(bV)) {
							M = aQ(M, bu + cK + cM + cu)
						}
					} else {
						if (cN === "top") {
							if (!j(bB)) {
								ci = aQ(ci, bm + cJ + cM + cF)
							}
						} else {
							if (cN === "bottom") {
								if (!j(bS)) {
									bU = aQ(bU, bm - cJ + cM + bG)
								}
							}
						}
					}
				}
			}
			if (b7) {
				each(ck, function(cP) {
					cP.getOffset()
				})
			}
			if (!j(bV)) {
				M += br[3]
			}
			if (!j(bB)) {
				ci += br[0]
			}
			if (!j(bS)) {
				bU += br[2]
			}
			if (!j(bz)) {
				co += br[1]
			}
			cn()
		};

		function cf() {
			var cJ;

			function cI() {
				var cL = bE.width || bK.offsetWidth,
					cK = bE.height || bK.offsetHeight;
				if (cL && cK) {
					if (cL !== cv || cK !== bR) {
						clearTimeout(cJ);
						cJ = setTimeout(function() {
							bk(cL, cK, false)
						}, 100)
					}
					cv = cL;
					bR = cK
				}
			}
			addEvent(aD, "resize", cI);
			addEvent(ct, "destroy", function() {
				removeEvent(aD, "resize", cI)
			})
		}
		function ca() {
			fireEvent(ct, "endResize", null, function() {
				bC -= 1
			})
		}
		bk = function(cK, cI, cL) {
			var cM = ct.title,
				cJ = ct.subtitle;
			bC += 1;
			ay(cL, ct);
			bX = bw;
			bs = cG;
			ct.chartWidth = cG = u(cK);
			ct.chartHeight = bw = u(cI);
			ax(cc, {
				width: cG + aj,
				height: bw + aj
			});
			b0.setSize(cG, bw, cL);
			be = cG - M - co;
			bo = bw - ci - bU;
			bh = null;
			each(ck, function(cN) {
				cN.isDirty = true;
				cN.setScale()
			});
			each(b3, function(cN) {
				cN.isDirty = true
			});
			ct.isDirtyLegend = true;
			ct.isDirtyBox = true;
			cp();
			if (cM) {
				cM.align(null, null, bT)
			}
			if (cJ) {
				cJ.align(null, null, bT)
			}
			bM(cL);
			bX = null;
			fireEvent(ct, "resize");
			if (n === false) {
				ca()
			} else {
				setTimeout(ca, (n && n.duration) || 500)
			}
		};
		cn = function() {
			ct.plotLeft = M = u(M);
			ct.plotTop = ci = u(ci);
			ct.plotWidth = be = u(cG - M - co);
			ct.plotHeight = bo = u(bw - ci - bU);
			ct.plotSizeX = bl ? bo : be;
			ct.plotSizeY = bl ? be : bo;
			bT = {
				x: cu,
				y: cF,
				width: cG - cu - bL,
				height: bw - cF - bG
			}
		};
		bq = function() {
			ci = b(bB, cF);
			co = b(bz, bL);
			bU = b(bS, bG);
			M = b(bV, cu);
			br = [0, 0, 0, 0]
		};
		b6 = function() {
			var cL = bE.borderWidth || 0,
				cJ = bE.backgroundColor,
				cK = bE.plotBackgroundColor,
				cN = bE.plotBackgroundImage,
				cI, cM = {
					x: M,
					y: ci,
					width: be,
					height: bo
				};
			cI = cL + (bE.shadow ? 8 : 0);
			if (cL || cJ) {
				if (!bx) {
					bx = b0.rect(cI / 2, cI / 2, cG - cI, bw - cI, bE.borderRadius, cL).attr({
						stroke: bE.borderColor,
						"stroke-width": cL,
						fill: cJ || ah
					}).add().shadow(bE.shadow)
				} else {
					bx.animate(bx.crisp(null, null, null, cG - cI, bw - cI))
				}
			}
			if (cK) {
				if (!bj) {
					bj = b0.rect(M, ci, be, bo, 0).attr({
						fill: cK
					}).add().shadow(bE.plotShadow)
				} else {
					bj.animate(cM)
				}
			}
			if (cN) {
				if (!b2) {
					b2 = b0.image(cN, M, ci, be, bo).add()
				} else {
					b2.animate(cM)
				}
			}
			if (bE.plotBorderWidth) {
				if (!bn) {
					bn = b0.rect(M, ci, be, bo, 0, bE.plotBorderWidth).attr({
						stroke: bE.plotBorderColor,
						"stroke-width": bE.plotBorderWidth,
						zIndex: 4
					}).add()
				} else {
					bn.animate(bn.crisp(null, M, ci, be, bo))
				}
			}
			ct.isDirtyBox = false
		};

		function bP() {
			var cI = bZ.labels;
			cD();
			cB = ct.legend = new cx();
			cp();
			each(ck, function(cJ) {
				cJ.setTickPositions(true)
			});
			b4();
			cp();
			b6();
			if (b7) {
				each(ck, function(cJ) {
					cJ.render()
				})
			}
			if (!ct.seriesGroup) {
				ct.seriesGroup = b0.g("series-group").attr({
					zIndex: 3
				}).add()
			}
			each(b3, function(cJ) {
				cJ.translate();
				cJ.setTooltipPoints();
				cJ.render()
			});
			if (cI.items) {
				each(cI.items, function() {
					var cK = aw(cI.style, this.style),
						cJ = p(cK.left) + M,
						cL = p(cK.top) + ci + 12;
					delete cK.left;
					delete cK.top;
					b0.text(this.html, cJ, cL).attr({
						zIndex: 2
					}).css(cK).add()
				})
			}
			if (!ct.toolbar) {
				ct.toolbar = cl()
			}
			bI();
			ct.hasRendered = true;
			if (cj) {
				bK.appendChild(cc);
				U(cj)
			}
		}
		function bf() {
			var cJ, cI = cc && cc.parentNode;
			if (ct === null) {
				return
			}
			fireEvent(ct, "destroy");
			removeEvent(aD, "unload", bf);
			removeEvent(ct);
			cJ = ck.length;
			while (cJ--) {
				ck[cJ] = ck[cJ].destroy()
			}
			cJ = b3.length;
			while (cJ--) {
				b3[cJ] = b3[cJ].destroy()
			}
			each(["title", "subtitle", "seriesGroup", "clipRect", "tracker"], function(cK) {
				var cL = ct[cK];
				if (cL) {
					ct[cK] = cL.destroy()
				}
			});
			each([bx, bn, bj, cB, bp, b0, L], function(cK) {
				if (cK && cK.destroy) {
					cK.destroy()
				}
			});
			bx = bn = bj = cB = bp = b0 = L = null;
			if (cc) {
				cc.innerHTML = "";
				removeEvent(cc);
				if (cI) {
					U(cc)
				}
				cc = null
			}
			clearInterval(b8);
			for (cJ in ct) {
				delete ct[cJ]
			}
			ct = null
		}
		function cb() {
			var cI = "onreadystatechange",
				cJ = "complete";
			if (!c && aD == aD.top && I.readyState !== cJ) {
				I.attachEvent(cI, function() {
					I.detachEvent(cI, cb);
					if (I.readyState === cJ) {
						cb()
					}
				});
				return
			}
			bY();
			bq();
			cn();
			each(bZ.series || [], function(cK) {
				cA(cK)
			});
			ct.inverted = bl = b(bl, bZ.chart.inverted);
			bQ();
			ct.render = bP;
			ct.tracker = L = new cs(bZ.tooltip);
			bP();
			fireEvent(ct, "load");
			if (bD) {
				bD.apply(ct, [ct])
			}
			each(ct.callbacks, function(cK) {
				cK.apply(ct, [ct])
			})
		}
		addEvent(aD, "unload", bf);
		if (bE.reflow !== false) {
			addEvent(ct, "load", cf)
		}
		if (bJ) {
			for (bA in bJ) {
				addEvent(ct, bA, bJ[bA])
			}
		}
		ct.options = bZ;
		ct.series = b3;
		ct.addSeries = cz;
		ct.animation = b(bE.animation, true);
		ct.destroy = bf;
		ct.get = ch;
		ct.getSelectedPoints = b1;
		ct.getSelectedSeries = cm;
		ct.hideLoading = cy;
		ct.isInsidePlot = by;
		ct.redraw = bM;
		ct.setSize = bk;
		ct.setTitle = cD;
		ct.showLoading = bH;
		ct.pointCount = 0;
		ct.counters = new A();
		cb()
	}
	a3.prototype.callbacks = [];
	var g = function() {};
	g.prototype = {
		init: function(be, M) {
			var L = this,
				bf = be.chart.counters,
				bg;
			L.series = be;
			L.applyOptions(M);
			L.pointAttr = {};
			if (be.options.colorByPoint) {
				bg = be.chart.options.colors;
				if (!L.options) {
					L.options = {}
				}
				L.color = L.options.color = L.color || bg[bf.color++];
				bf.wrapColor(bg.length)
			}
			be.chart.pointCount++;
			return L
		},
		applyOptions: function(M) {
			var L = this,
				be = L.series;
			L.config = M;
			if (V(M) || M === null) {
				L.y = M
			} else {
				if (F(M) && !V(M.length)) {
					aw(L, M);
					L.options = M
				} else {
					if (a1(M[0])) {
						L.name = M[0];
						L.y = M[1]
					} else {
						if (V(M[0])) {
							L.x = M[0];
							L.y = M[1]
						}
					}
				}
			}
			if (L.x === aA) {
				L.x = be.autoIncrement()
			}
		},
		destroy: function() {
			var L = this,
				M = L.series,
				be = M.chart.hoverPoints,
				bf;
			M.chart.pointCount--;
			if (be) {
				L.setState();
				aO(be, L)
			}
			if (L === M.chart.hoverPoint) {
				L.onMouseOut()
			}
			removeEvent(L);
			each(["graphic", "tracker", "group", "dataLabel", "connector", "shadowGroup"], function(bg) {
				if (L[bg]) {
					L[bg].destroy()
				}
			});
			if (L.legendItem) {
				L.series.chart.legend.destroyItem(L)
			}
			for (bf in L) {
				L[bf] = null
			}
		},
		getLabelConfig: function() {
			var L = this;
			return {
				x: L.category,
				y: L.y,
				series: L.series,
				point: L,
				percentage: L.percentage,
				total: L.total || L.stackTotal
			}
		},
		select: function(bg, M) {
			var L = this,
				be = L.series,
				bf = be.chart;
			bg = b(bg, !L.selected);
			L.firePointEvent(bg ? "select" : "unselect", {
				accumulate: M
			}, function() {
				L.selected = bg;
				L.setState(bg && v);
				if (!M) {
					each(bf.getSelectedPoints(), function(bh) {
						if (bh.selected && bh !== L) {
							bh.selected = false;
							bh.setState(Y);
							bh.firePointEvent("unselect")
						}
					})
				}
			})
		},
		onMouseOver: function() {
			var L = this,
				be = L.series.chart,
				bf = be.tooltip,
				M = be.hoverPoint;
			if (M && M !== L) {
				M.onMouseOut()
			}
			L.firePointEvent("mouseOver");
			if (bf && !bf.shared) {
				bf.refresh(L)
			}
			L.setState(S);
			be.hoverPoint = L
		},
		onMouseOut: function() {
			var L = this;
			L.firePointEvent("mouseOut");
			L.setState();
			L.series.chart.hoverPoint = null
		},
		tooltipFormatter: function(be) {
			var L = this,
				M = L.series;
			return ['<span style="color:' + M.color + '">', (L.name || M.name), "</span>: ", (!be ? ("<b>x = " + (L.name || L.x) + ",</b> ") : ""), "<b>", (!be ? "y = " : ""), L.y, "</b>"].join("")
		},
		update: function(M, bi, bg) {
			var L = this,
				be = L.series,
				bh = L.graphic,
				bf = be.chart;
			bi = b(bi, true);
			L.firePointEvent("update", {
				options: M
			}, function() {
				L.applyOptions(M);
				if (F(M)) {
					be.getAttribs();
					if (bh) {
						bh.attr(L.pointAttr[be.state])
					}
				}
				be.isDirty = true;
				if (bi) {
					bf.redraw(bg)
				}
			})
		},
		remove: function(bh, bg) {
			var L = this,
				M = L.series,
				be = M.chart,
				bf = M.data;
			ay(bg, be);
			bh = b(bh, true);
			L.firePointEvent("remove", null, function() {
				aO(bf, L);
				L.destroy();
				M.isDirty = true;
				if (bh) {
					be.redraw()
				}
			})
		},
		firePointEvent: function(bh, bf, M) {
			var L = this,
				bg = this.series,
				be = bg.options;
			if (be.point.events[bh] || (L.options && L.options.events && L.options.events[bh])) {
				this.importEvents()
			}
			if (bh === "click" && be.allowPointSelect) {
				M = function(bi) {
					L.select(null, bi.ctrlKey || bi.metaKey || bi.shiftKey)
				}
			}
			fireEvent(this, bh, bf, M)
		},
		importEvents: function() {
			if (!this.hasImportedEvents) {
				var L = this,
					M = merge(L.series.options.point, L.options),
					bf = M.events,
					be;
				L.events = bf;
				for (be in bf) {
					addEvent(L, be, bf[be])
				}
				this.hasImportedEvents = true
			}
		},
		setState: function(M) {
			var bl = this,
				bh = bl.series,
				bm = bh.options.states,
				be = bd[bh.type].marker && bh.options.marker,
				bg = be && !be.enabled,
				bf = be && be.states[M],
				bi = bf && bf.enabled === false,
				bk = bh.stateMarkerGraphic,
				bj = bh.chart,
				L = bl.pointAttr;
			M = M || Y;
			if (M === bl.state || (bl.selected && M !== v) || (bm[M] && bm[M].enabled === false) || (M && (bi || (bg && !bf.enabled)))) {
				return
			}
			if (bl.graphic) {
				bl.graphic.attr(L[M])
			} else {
				if (M) {
					if (!bk) {
						bh.stateMarkerGraphic = bk = bj.renderer.circle(0, 0, L[M].r).attr(L[M]).add(bh.group)
					}
					bk.translate(bl.plotX, bl.plotY)
				}
				if (bk) {
					bk[M ? "show" : "hide"]()
				}
			}
			bl.state = M
		}
	};
	var ap = function() {};
	ap.prototype = {
		isCartesian: true,
		type: "line",
		pointClass: g,
		pointAttrToOptions: {
			stroke: "lineColor",
			"stroke-width": "lineWidth",
			fill: "fillColor",
			r: "radius"
		},
		init: function(bh, M) {
			var bg = this,
				bf, be, L = bh.series.length;
			bg.chart = bh;
			M = bg.setOptions(M);
			aw(bg, {
				index: L,
				options: M,
				name: M.name || "Series " + (L + 1),
				state: Y,
				pointAttr: {},
				visible: M.visible !== false,
				selected: M.selected === true
			});
			be = M.events;
			for (bf in be) {
				addEvent(bg, bf, be[bf])
			}
			if ((be && be.click) || (M.point && M.point.events && M.point.events.click) || M.allowPointSelect) {
				bh.runTrackerClick = true
			}
			bg.getColor();
			bg.getSymbol();
			bg.setData(M.data, false)
		},
		autoIncrement: function() {
			var M = this,
				L = M.options,
				be = M.xIncrement;
			be = b(be, L.pointStart, 0);
			M.pointInterval = b(M.pointInterval, L.pointInterval, 1);
			M.xIncrement = be + M.pointInterval;
			return be
		},
		cleanData: function() {
			var bf = this,
				bg = bf.chart,
				bh = bf.data,
				bj, be, bi = bg.smallestInterval,
				L, M;
			K(bh, function(bl, bk) {
				return (bl.x - bk.x)
			});
			if (bf.options.connectNulls) {
				for (M = bh.length - 1; M >= 0; M--) {
					if (bh[M].y === null && bh[M - 1] && bh[M + 1]) {
						bh.splice(M, 1)
					}
				}
			}
			for (M = bh.length - 1; M >= 0; M--) {
				if (bh[M - 1]) {
					L = bh[M].x - bh[M - 1].x;
					if (L > 0 && (be === aA || L < be)) {
						be = L;
						bj = M
					}
				}
			}
			if (bi === aA || be < bi) {
				bg.smallestInterval = be
			}
			bf.closestPoints = bj
		},
		getSegments: function() {
			var L = -1,
				M = [],
				be = this.data;
			each(be, function(bf, bg) {
				if (bf.y === null) {
					if (bg > L + 1) {
						M.push(be.slice(L + 1, bg))
					}
					L = bg
				} else {
					if (bg === be.length - 1) {
						M.push(be.slice(L + 1, bg + 1))
					}
				}
			});
			this.segments = M
		},
		setOptions: function(be) {
			var L = this.chart.options.plotOptions,
				M = merge(L[this.type], L.series, be);
			return M
		},
		getColor: function() {
			var M = this.chart.options.colors,
				L = this.chart.counters;
			this.color = this.options.color || M[L.color++] || "#0000ff";
			L.wrapColor(M.length)
		},
		getSymbol: function() {
			var L = this.chart.options.symbols,
				M = this.chart.counters;
			this.symbol = this.options.marker.symbol || L[M.symbol++];
			M.wrapSymbol(L.length)
		},
		addPoint: function(bl, bk, M, be) {
			var bg = this,
				bf = bg.data,
				bj = bg.graph,
				L = bg.area,
				bh = bg.chart,
				bi = (new bg.pointClass()).init(bg, bl);
			ay(be, bh);
			if (bj && M) {
				bj.shift = M
			}
			if (L) {
				L.shift = M;
				L.isArea = true
			}
			bk = b(bk, true);
			bf.push(bi);
			if (M) {
				bf[0].remove(false)
			}
			bg.getAttribs();
			bg.isDirty = true;
			if (bk) {
				bh.redraw()
			}
		},
		setData: function(bg, bi) {
			var M = this,
				bh = M.data,
				bf = M.initialColor,
				be = M.chart,
				L = (bh && bh.length) || 0;
			M.xIncrement = null;
			if (j(bf)) {
				be.counters.color = bf
			}
			bg = map(af(bg || []), function(bj) {
				return (new M.pointClass()).init(M, bj)
			});
			while (L--) {
				bh[L].destroy()
			}
			M.data = bg;
			M.cleanData();
			M.getSegments();
			M.getAttribs();
			M.isDirty = true;
			be.isDirtyBox = true;
			if (b(bi, true)) {
				be.redraw(false)
			}
		},
		remove: function(bf, be) {
			var L = this,
				M = L.chart;
			bf = b(bf, true);
			if (!L.isRemoving) {
				L.isRemoving = true;
				fireEvent(L, "remove", null, function() {
					L.destroy();
					M.isDirtyLegend = M.isDirtyBox = true;
					if (bf) {
						M.redraw(be)
					}
				})
			}
			L.isRemoving = false
		},
		translate: function() {
			var bg = this,
				bk = bg.chart,
				bj = bg.options.stacking,
				bi = bg.xAxis.categories,
				L = bg.yAxis,
				bf = bg.data,
				bh = bf.length;
			while (bh--) {
				var bo = bf[bh],
					M = bo.x,
					bm = bo.y,
					bl = bo.low,
					bn = L.stacks[(bm < 0 ? "-" : "") + bg.stackKey],
					bp, be;
				bo.plotX = bg.xAxis.translate(M);
				if (bj && bg.visible && bn && bn[M]) {
					bp = bn[M];
					be = bp.total;
					bp.cum = bl = bp.cum - bm;
					bm = bl + bm;
					if (bj === "percent") {
						bl = be ? bl * 100 / be : 0;
						bm = be ? bm * 100 / be : 0
					}
					bo.percentage = be ? bo.y * 100 / be : 0;
					bo.stackTotal = be
				}
				if (j(bl)) {
					bo.yBottom = L.translate(bl, 0, 1, 0, 1)
				}
				if (bm !== null) {
					bo.plotY = L.translate(bm, 0, 1, 0, 1)
				}
				bo.clientX = bk.inverted ? bk.plotHeight - bo.plotX : bo.plotX;
				bo.category = bi && bi[bo.x] !== aA ? bi[bo.x] : bo.x
			}
		},
		setTooltipPoints: function(bf) {
			var bh = this,
				bi = bh.chart,
				M = bi.inverted,
				bg = [],
				bk = u((M ? bi.plotTop : bi.plotLeft) + bi.plotSizeX),
				bj, be, L = [];
			if (bf) {
				bh.tooltipPoints = null
			}
			each(bh.segments, function(bl) {
				bg = bg.concat(bl)
			});
			if (bh.xAxis && bh.xAxis.reversed) {
				bg = bg.reverse()
			}
			each(bg, function(bl, bm) {
				bj = bg[bm - 1] ? bg[bm - 1]._high + 1 : 0;
				be = bl._high = bg[bm + 1] ? (R((bl.plotX + (bg[bm + 1] ? bg[bm + 1].plotX : bk)) / 2)) : bk;
				while (bj <= be) {
					L[M ? bk - bj++ : bj++] = bl
				}
			});
			bh.tooltipPoints = L
		},
		onMouseOver: function() {
			var M = this,
				be = M.chart,
				L = be.hoverSeries;
			if (!aZ && be.mouseIsDown) {
				return
			}
			if (L && L !== M) {
				L.onMouseOut()
			}
			if (M.options.events.mouseOver) {
				fireEvent(M, "mouseOver")
			}
			if (M.tracker) {
				M.tracker.toFront()
			}
			M.setState(S);
			be.hoverSeries = M
		},
		onMouseOut: function() {
			var be = this,
				M = be.options,
				bf = be.chart,
				bg = bf.tooltip,
				L = bf.hoverPoint;
			if (L) {
				L.onMouseOut()
			}
			if (be && M.events.mouseOut) {
				fireEvent(be, "mouseOut")
			}
			if (bg && !M.stickyTracking) {
				bg.hide()
			}
			be.setState();
			bf.hoverSeries = null
		},
		animate: function(bg) {
			var M = this,
				be = M.chart,
				L = M.clipRect,
				bf = M.options.animation;
			if (bf && !F(bf)) {
				bf = {}
			}
			if (bg) {
				if (!L.isAnimating) {
					L.attr("width", 0);
					L.isAnimating = true
				}
			} else {
				L.animate({
					width: be.plotSizeX
				}, bf);
				this.animate = null
			}
		},
		drawPoints: function() {
			var bg = this,
				L, bf = bg.data,
				bj = bg.chart,
				M, bl, bh, bk, bi, be;
			if (bg.options.marker.enabled) {
				bh = bf.length;
				while (bh--) {
					bk = bf[bh];
					M = bk.plotX;
					bl = bk.plotY;
					be = bk.graphic;
					if (bl !== aA && !isNaN(bl)) {
						L = bk.pointAttr[bk.selected ? v : Y];
						bi = L.r;
						if (be) {
							be.animate({
								x: M,
								y: bl,
								r: bi
							})
						} else {
							bk.graphic = bj.renderer.symbol(b(bk.marker && bk.marker.symbol, bg.symbol), M, bl, bi).attr(L).add(bg.group)
						}
					}
				}
			}
		},
		convertAttribs: function(be, M, bj, bi) {
			var bg = this.pointAttrToOptions,
				L, bf, bh = {};
			be = be || {};
			M = M || {};
			bj = bj || {};
			bi = bi || {};
			for (L in bg) {
				bf = bg[L];
				bh[L] = b(be[bf], M[L], bj[L], bi[L])
			}
			return bh
		},
		getAttribs: function() {
			var bk = this,
				be = bd[bk.type].marker ? bk.options.marker : bk.options,
				bq = be.states,
				M = bq[S],
				bl, bf = bk.color,
				bm = {
					stroke: bf,
					fill: bf
				},
				bj = bk.data,
				bi, bo, bh = [],
				L, bg = bk.pointAttrToOptions,
				bn, bp;
			if (bk.options.marker) {
				M.radius = M.radius || be.radius + 2;
				M.lineWidth = M.lineWidth || be.lineWidth + 1
			} else {
				M.color = M.color || w(M.color || bf).brighten(M.brightness).get()
			}
			bh[Y] = bk.convertAttribs(be, bm);
			each([S, v], function(br) {
				bh[br] = bk.convertAttribs(bq[br], bh[Y])
			});
			bk.pointAttr = bh;
			bi = bj.length;
			while (bi--) {
				bo = bj[bi];
				be = (bo.options && bo.options.marker) || bo.options;
				if (be && be.enabled === false) {
					be.radius = 0
				}
				bn = false;
				if (bo.options) {
					for (bp in bg) {
						if (j(be[bg[bp]])) {
							bn = true
						}
					}
				}
				if (bn) {
					L = [];
					bq = be.states || {};
					bl = bq[S] = bq[S] || {};
					if (!bk.options.marker) {
						bl.color = w(bl.color || bo.options.color).brighten(bl.brightness || M.brightness).get()
					}
					L[Y] = bk.convertAttribs(be, bh[Y]);
					L[S] = bk.convertAttribs(bq[S], bh[S], L[Y]);
					L[v] = bk.convertAttribs(bq[v], bh[v], L[Y])
				} else {
					L = bh
				}
				bo.pointAttr = L
			}
		},
		destroy: function() {
			var be = this,
				bf = be.chart,
				L = be.clipRect,
				bg = /\/5[0-9\.]+ (Safari|Mobile)\//.test(e),
				M, bh;
			fireEvent(be, "destroy");
			removeEvent(be);
			if (be.legendItem) {
				be.chart.legend.destroyItem(be)
			}
			each(be.data, function(bi) {
				bi.destroy()
			});
			if (L && L !== bf.clipRect) {
				be.clipRect = L.destroy()
			}
			each(["area", "graph", "dataLabelsGroup", "group", "tracker"], function(bi) {
				if (be[bi]) {
					M = bg && bi === "group" ? "hide" : "destroy";
					be[bi][M]()
				}
			});
			if (bf.hoverSeries === be) {
				bf.hoverSeries = null
			}
			aO(bf.series, be);
			for (bh in be) {
				delete be[bh]
			}
		},
		drawDataLabels: function() {
			if (this.options.dataLabels.enabled) {
				var bh = this,
					bq, bo, bg = bh.data,
					L = bh.options,
					bs = L.dataLabels,
					bn, bk = bh.dataLabelsGroup,
					bm = bh.chart,
					bl = bm.renderer,
					M = bm.inverted,
					bf = bh.type,
					be, bi = L.stacking,
					bp = bf === "column" || bf === "bar",
					bj = bs.verticalAlign === null,
					br = bs.y === null;
				if (bp) {
					if (bi) {
						if (bj) {
							bs = merge(bs, {
								verticalAlign: "middle"
							})
						}
						if (br) {
							bs = merge(bs, {
								y: {
									top: 14,
									middle: 4,
									bottom: -6
								}[bs.verticalAlign]
							})
						}
					} else {
						if (bj) {
							bs = merge(bs, {
								verticalAlign: "top"
							})
						}
					}
				}
				if (!bk) {
					bk = bh.dataLabelsGroup = bl.g("data-labels").attr({
						visibility: bh.visible ? al : at,
						zIndex: 6
					}).translate(bm.plotLeft, bm.plotTop).add()
				} else {
					bk.translate(bm.plotLeft, bm.plotTop)
				}
				be = bs.color;
				if (be === "auto") {
					be = null
				}
				bs.style.color = b(be, bh.color, "black");
				each(bg, function(bz) {
					var bt = bz.barX,
						bu = (bt && bt + bz.barW / 2) || bz.plotX || -999,
						bC = b(bz.plotY, -999),
						bB = bz.dataLabel,
						bw = bs.align,
						by = br ? (bz.y >= 0 ? -6 : 12) : bs.y;
					bn = bs.formatter.call(bz.getLabelConfig());
					bq = (M ? bm.plotWidth - bC : bu) + bs.x;
					bo = (M ? bm.plotHeight - bu : bC) + by;
					if (bf === "column") {
						bq += {
							left: -1,
							right: 1
						}[bw] * bz.barW / 2 || 0
					}
					if (M && bz.y < 0) {
						bw = "right";
						bq -= 10
					}
					if (bB) {
						if (M && !bs.y) {
							bo = bo + p(bB.styles.lineHeight) * 0.9 - bB.getBBox().height / 2
						}
						bB.attr({
							text: bn
						}).animate({
							x: bq,
							y: bo
						})
					} else {
						if (j(bn)) {
							bB = bz.dataLabel = bl.text(bn, bq, bo).attr({
								align: bw,
								rotation: bs.rotation,
								zIndex: 1
							}).css(bs.style).add(bk);
							if (M && !bs.y) {
								bB.attr({
									y: bo + p(bB.styles.lineHeight) * 0.9 - bB.getBBox().height / 2
								})
							}
						}
					}
					if (bp && L.stacking && bB) {
						var bA = bz.barY,
							bv = bz.barW,
							bx = bz.barH;
						bB.align(bs, null, {
							x: M ? bm.plotWidth - bA - bx : bt,
							y: M ? bm.plotHeight - bt - bv : bA,
							width: M ? bx : bv,
							height: M ? bv : bx
						})
					}
				})
			}
		},
		drawGraph: function() {
			var bj = this,
				be = bj.options,
				bn = bj.chart,
				M = bj.graph,
				bk = [],
				bg, bs = bj.area,
				bi = bj.group,
				br = be.lineColor || bj.color,
				L = be.lineWidth,
				bh = be.dashStyle,
				bf, bq = bn.renderer,
				bl = bj.yAxis.getThreshold(be.threshold || 0),
				bt = /^area/.test(bj.type),
				bo = [],
				bp = [],
				bm;
			each(bj.segments, function(bw) {
				bf = [];
				each(bw, function(by, bz) {
					if (bj.getPointSpline) {
						bf.push.apply(bf, bj.getPointSpline(bw, by, bz))
					} else {
						bf.push(bz ? av : au);
						if (bz && be.step) {
							var bA = bw[bz - 1];
							bf.push(by.plotX, bA.plotY)
						}
						bf.push(by.plotX, by.plotY)
					}
				});
				if (bw.length > 1) {
					bk = bk.concat(bf)
				} else {
					bo.push(bw[0])
				}
				if (bt) {
					var bx = [],
						bv, bu = bf.length;
					for (bv = 0; bv < bu; bv++) {
						bx.push(bf[bv])
					}
					if (bu === 3) {
						bx.push(av, bf[1], bf[2])
					}
					if (be.stacking && bj.type !== "areaspline") {
						for (bv = bw.length - 1; bv >= 0; bv--) {
							bx.push(bw[bv].plotX, bw[bv].yBottom)
						}
					} else {
						bx.push(av, bw[bw.length - 1].plotX, bl, av, bw[0].plotX, bl)
					}
					bp = bp.concat(bx)
				}
			});
			bj.graphPath = bk;
			bj.singlePoints = bo;
			if (bt) {
				bg = b(be.fillColor, w(bj.color).setOpacity(be.fillOpacity || 0.75).get());
				if (bs) {
					bs.animate({
						d: bp
					})
				} else {
					bj.area = bj.chart.renderer.path(bp).attr({
						fill: bg
					}).add(bi)
				}
			}
			if (M) {
				stop(M);
				M.animate({
					d: bk
				})
			} else {
				if (L) {
					bm = {
						stroke: br,
						"stroke-width": L
					};
					if (bh) {
						bm.dashstyle = bh
					}
					bj.graph = bq.path(bk).attr(bm).add(bi).shadow(be.shadow)
				}
			}
		},
		render: function() {
			var bf = this,
				bh = bf.chart,
				bi, bj, bl = bf.options,
				M = bl.animation,
				bk = M && bf.animate,
				L = bk ? (M && M.duration) || 500 : 0,
				be = bf.clipRect,
				bg = bh.renderer;
			if (!be) {
				be = bf.clipRect = !bh.hasRendered && bh.clipRect ? bh.clipRect : bg.clipRect(0, 0, bh.plotSizeX, bh.plotSizeY);
				if (!bh.clipRect) {
					bh.clipRect = be
				}
			}
			if (!bf.group) {
				bi = bf.group = bg.g("series");
				if (bh.inverted) {
					bj = function() {
						bi.attr({
							width: bh.plotWidth,
							height: bh.plotHeight
						}).invert()
					};
					bj();
					addEvent(bh, "resize", bj);
					addEvent(bf, "destroy", function() {
						removeEvent(bh, "resize", bj)
					})
				}
				bi.clip(bf.clipRect).attr({
					visibility: bf.visible ? al : at,
					zIndex: bl.zIndex
				}).translate(bh.plotLeft, bh.plotTop).add(bh.seriesGroup)
			}
			bf.drawDataLabels();
			if (bk) {
				bf.animate(true)
			}
			if (bf.drawGraph) {
				bf.drawGraph()
			}
			bf.drawPoints();
			if (bf.options.enableMouseTracking !== false) {
				bf.drawTracker()
			}
			if (bk) {
				bf.animate()
			}
			setTimeout(function() {
				be.isAnimating = false;
				bi = bf.group;
				if (bi && be !== bh.clipRect && be.renderer) {
					bi.clip((bf.clipRect = bh.clipRect));
					be.destroy()
				}
			}, L);
			bf.isDirty = false
		},
		redraw: function() {
			var L = this,
				M = L.chart,
				be = L.group;
			if (be) {
				if (M.inverted) {
					be.attr({
						width: M.plotWidth,
						height: M.plotHeight
					})
				}
				be.animate({
					translateX: M.plotLeft,
					translateY: M.plotTop
				})
			}
			L.translate();
			L.setTooltipPoints(true);
			L.render()
		},
		setState: function(bg) {
			var be = this,
				M = be.options,
				bf = be.graph,
				bh = M.states,
				L = M.lineWidth;
			bg = bg || Y;
			if (be.state !== bg) {
				be.state = bg;
				if (bh[bg] && bh[bg].enabled === false) {
					return
				}
				if (bg) {
					L = bh[bg].lineWidth || L + 1
				}
				if (bf && !bf.dashstyle) {
					bf.attr({
						"stroke-width": L
					}, bg ? 0 : 500)
				}
			}
		},
		setVisible: function(M, bn) {
			var bg = this,
				bk = bg.chart,
				bj = bg.legendItem,
				bl = bg.group,
				bp = bg.tracker,
				bi = bg.dataLabelsGroup,
				L, bf, be = bg.data,
				bm, bo = bk.options.chart.ignoreHiddenSeries,
				bh = bg.visible;
			bg.visible = M = M === aA ? !bh : M;
			L = M ? "show" : "hide";
			if (bl) {
				bl[L]()
			}
			if (bp) {
				bp[L]()
			} else {
				bf = be.length;
				while (bf--) {
					bm = be[bf];
					if (bm.tracker) {
						bm.tracker[L]()
					}
				}
			}
			if (bi) {
				bi[L]()
			}
			if (bj) {
				bk.legend.colorizeItem(bg, M)
			}
			bg.isDirty = true;
			if (bg.options.stacking) {
				each(bk.series, function(bq) {
					if (bq.options.stacking && bq.visible) {
						bq.isDirty = true
					}
				})
			}
			if (bo) {
				bk.isDirtyBox = true
			}
			if (bn !== false) {
				bk.redraw()
			}
			fireEvent(bg, L)
		},
		show: function() {
			this.setVisible(true)
		},
		hide: function() {
			this.setVisible(false)
		},
		select: function(M) {
			var L = this;
			L.selected = M = (M === aA) ? !L.selected : M;
			if (L.checkbox) {
				L.checkbox.checked = M
			}
			fireEvent(L, M ? "select" : "unselect")
		},
		drawTracker: function() {
			var bh = this,
				bn = bh.options,
				L = [].concat(bh.graphPath),
				bk = L.length,
				bj = bh.chart,
				M = bj.options.tooltip.snap,
				bl = bh.tracker,
				bm = bn.cursor,
				bi = bm && {
					cursor: bm
				},
				bg = bh.singlePoints,
				be, bf;
			if (bk) {
				bf = bk + 1;
				while (bf--) {
					if (L[bf] === au) {
						L.splice(bf + 1, 0, L[bf + 1] - M, L[bf + 2], av)
					}
					if ((bf && L[bf] === au) || bf === bk) {
						L.splice(bf, 0, av, L[bf - 2] + M, L[bf - 1])
					}
				}
			}
			for (bf = 0; bf < bg.length; bf++) {
				be = bg[bf];
				L.push(au, be.plotX - M, be.plotY, av, be.plotX + M, be.plotY)
			}
			if (bl) {
				bl.attr({
					d: L
				})
			} else {
				bh.tracker = bj.renderer.path(L).attr({
					isTracker: true,
					stroke: aF,
					fill: ah,
					"stroke-width": bn.lineWidth + 2 * M,
					visibility: bh.visible ? al : at,
					zIndex: bn.zIndex || 1
				}).on(aZ ? "touchstart" : "mouseover", function() {
					if (bj.hoverSeries !== bh) {
						bh.onMouseOver()
					}
				}).on("mouseout", function() {
					if (!bn.stickyTracking) {
						bh.onMouseOut()
					}
				}).css(bi).add(bj.trackerGroup)
			}
		}
	};
	var O = aP(ap);
	a6.line = O;
	var r = aP(ap, {
		type: "area"
	});
	a6.area = r;
	var x = aP(ap, {
		type: "spline",
		getPointSpline: function(M, bp, bq) {
			var bn = 1.5,
				L = bn + 1,
				bg = bp.plotX,
				be = bp.plotY,
				bf = M[bq - 1],
				br = M[bq + 1],
				bk, bj, bm, bl, bu;
			if (bq && bq < M.length - 1) {
				var bi = bf.plotX,
					bh = bf.plotY,
					bt = br.plotX,
					bs = br.plotY,
					bo;
				bk = (bn * bg + bi) / L;
				bj = (bn * be + bh) / L;
				bm = (bn * bg + bt) / L;
				bl = (bn * be + bs) / L;
				bo = ((bl - bj) * (bm - bg)) / (bm - bk) + be - bl;
				bj += bo;
				bl += bo;
				if (bj > bh && bj > be) {
					bj = aQ(bh, be);
					bl = 2 * be - bj
				} else {
					if (bj < bh && bj < be) {
						bj = ad(bh, be);
						bl = 2 * be - bj
					}
				}
				if (bl > bs && bl > be) {
					bl = aQ(bs, be);
					bj = 2 * be - bl
				} else {
					if (bl < bs && bl < be) {
						bl = ad(bs, be);
						bj = 2 * be - bl
					}
				}
				bp.rightContX = bm;
				bp.rightContY = bl
			}
			if (!bq) {
				bu = [au, bg, be]
			} else {
				bu = ["C", bf.rightContX || bf.plotX, bf.rightContY || bf.plotY, bk || bg, bj || be, bg, be];
				bf.rightContX = bf.rightContY = null
			}
			return bu
		}
	});
	a6.spline = x;
	var a = aP(x, {
		type: "areaspline"
	});
	a6.areaspline = a;
	var aL = aP(ap, {
		type: "column",
		pointAttrToOptions: {
			stroke: "borderColor",
			"stroke-width": "borderWidth",
			fill: "color",
			r: "borderRadius"
		},
		init: function() {
			ap.prototype.init.apply(this, arguments);
			var L = this,
				M = L.chart;
			M.hasColumn = true;
			if (M.hasRendered) {
				each(M.series, function(be) {
					if (be.type === L.type) {
						be.isDirty = true
					}
				})
			}
		},
		translate: function() {
			var bm = this,
				bp = bm.chart,
				bg = bm.options,
				be = bg.stacking,
				bA = bg.borderWidth,
				bw = 0,
				L = bm.xAxis.reversed,
				bs = bm.xAxis.categories,
				by = {},
				M, bx;
			ap.prototype.translate.apply(bm);
			each(bp.series, function(bB) {
				if (bB.type === bm.type && bB.visible) {
					if (bB.options.stacking) {
						M = bB.stackKey;
						if (by[M] === aA) {
							by[M] = bw++
						}
						bx = by[M]
					} else {
						bx = bw++
					}
					bB.columnIndex = bx
				}
			});
			var bz = bm.data,
				bh = bm.closestPoints,
				bi = ba(bz[1] ? bz[bh].plotX - bz[bh - 1].plotX : bp.plotSizeX / ((bs && bs.length) || 1)),
				bu = bi * bg.groupPadding,
				bn = bi - 2 * bu,
				bt = bn / bw,
				br = bg.pointWidth,
				bl = j(br) ? (bt - br) / 2 : bt * bg.pointPadding,
				bj = aQ(b(br, bt - 2 * bl), 1),
				bq = (L ? bw - bm.columnIndex : bm.columnIndex) || 0,
				bv = bl + (bu + bq * bt - (bi / 2)) * (L ? -1 : 1),
				bf = bg.threshold || 0,
				bo = bm.yAxis.getThreshold(bf),
				bk = b(bg.minPointLength, 5);
			each(bz, function(bH) {
				var bJ = bH.plotY,
					bE = bH.yBottom || bo,
					bB = bH.plotX + bv,
					bI = bc(ad(bJ, bE)),
					bF = bc(aQ(bJ, bE) - bI),
					bG = bm.yAxis.stacks[(bH.y < 0 ? "-" : "") + bm.stackKey],
					bD, bC;
				if (be && bm.visible && bG && bG[bH.x]) {
					bG[bH.x].setOffset(bv, bj)
				}
				if (ba(bF) < bk) {
					if (bk) {
						bF = bk;
						bI = ba(bI - bo) > bk ? bE - bk : bo - (bJ <= bo ? bk : 0)
					}
					bD = bI - 3
				}
				aw(bH, {
					barX: bB,
					barY: bI,
					barW: bj,
					barH: bF
				});
				bH.shapeType = "rect";
				bC = aw(bp.renderer.Element.prototype.crisp.apply({}, [bA, bB, bI, bj, bF]), {
					r: bg.borderRadius
				});
				if (bA % 2) {
					bC.y -= 1;
					bC.height += 1
				}
				bH.shapeArgs = bC;
				bH.trackerArgs = j(bD) && merge(bH.shapeArgs, {
					height: aQ(6, bF + 3),
					y: bD
				})
			})
		},
		getSymbol: function() {},
		drawGraph: function() {},
		drawPoints: function() {
			var be = this,
				L = be.options,
				bf = be.chart.renderer,
				bg, M;
			each(be.data, function(bh) {
				var bi = bh.plotY;
				if (bi !== aA && !isNaN(bi) && bh.y !== null) {
					bg = bh.graphic;
					M = bh.shapeArgs;
					if (bg) {
						stop(bg);
						bg.animate(M)
					} else {
						bh.graphic = bf[bh.shapeType](M).attr(bh.pointAttr[bh.selected ? v : Y]).add(be.group).shadow(L.shadow)
					}
				}
			})
		},
		drawTracker: function() {
			var M = this,
				bh = M.chart,
				bg = bh.renderer,
				L, bi, bf = +new Date(),
				bl = M.options,
				bj = bl.cursor,
				be = bj && {
					cursor: bj
				},
				bk;
			each(M.data, function(bm) {
				bi = bm.tracker;
				L = bm.trackerArgs || bm.shapeArgs;
				delete L.strokeWidth;
				if (bm.y !== null) {
					if (bi) {
						bi.attr(L)
					} else {
						bm.tracker = bg[bm.shapeType](L).attr({
							isTracker: bf,
							fill: aF,
							visibility: M.visible ? al : at,
							zIndex: bl.zIndex || 1
						}).on(aZ ? "touchstart" : "mouseover", function(bn) {
							bk = bn.relatedTarget || bn.fromElement;
							if (bh.hoverSeries !== M && ac(bk, "isTracker") !== bf) {
								M.onMouseOver()
							}
							bm.onMouseOver()
						}).on("mouseout", function(bn) {
							if (!bl.stickyTracking) {
								bk = bn.relatedTarget || bn.toElement;
								if (ac(bk, "isTracker") !== bf) {
									M.onMouseOut()
								}
							}
						}).css(be).add(bm.group || bh.trackerGroup)
					}
				}
			})
		},
		animate: function(be) {
			var L = this,
				M = L.data;
			if (!be) {
				each(M, function(bf) {
					var bh = bf.graphic,
						bg = bf.shapeArgs;
					if (bh) {
						bh.attr({
							height: 0,
							y: L.yAxis.translate(0, 0, 1)
						});
						bh.animate({
							height: bg.height,
							y: bg.y
						}, L.options.animation)
					}
				});
				L.animate = null
			}
		},
		remove: function() {
			var L = this,
				M = L.chart;
			if (M.hasRendered) {
				each(M.series, function(be) {
					if (be.type === L.type) {
						be.isDirty = true
					}
				})
			}
			ap.prototype.remove.apply(L, arguments)
		}
	});
	a6.column = aL;
	var ab = aP(aL, {
		type: "bar",
		init: function(L) {
			L.inverted = this.inverted = true;
			aL.prototype.init.apply(this, arguments)
		}
	});
	a6.bar = ab;
	var T = aP(ap, {
		type: "scatter",
		translate: function() {
			var L = this;
			ap.prototype.translate.apply(L);
			each(L.data, function(M) {
				M.shapeType = "circle";
				M.shapeArgs = {
					x: M.plotX,
					y: M.plotY,
					r: L.chart.options.tooltip.snap
				}
			})
		},
		drawTracker: function() {
			var M = this,
				be = M.options.cursor,
				L = be && {
					cursor: be
				},
				bf;
			each(M.data, function(bg) {
				bf = bg.graphic;
				if (bf) {
					bf.attr({
						isTracker: true
					}).on("mouseover", function() {
						M.onMouseOver();
						bg.onMouseOver()
					}).on("mouseout", function() {
						if (!M.options.stickyTracking) {
							M.onMouseOut()
						}
					}).css(L)
				}
			})
		},
		cleanData: function() {}
	});
	a6.scatter = T;
	var Z = aP(g, {
		init: function() {
			g.prototype.init.apply(this, arguments);
			var L = this,
				M;
			aw(L, {
				visible: L.visible !== false,
				name: b(L.name, "Slice")
			});
			M = function() {
				L.slice()
			};
			addEvent(L, "select", M);
			addEvent(L, "unselect", M);
			return L
		},
		setVisible: function(bi) {
			var L = this,
				bg = L.series.chart,
				bh = L.tracker,
				bf = L.dataLabel,
				M = L.connector,
				be = L.shadowGroup,
				bj;
			L.visible = bi = bi === aA ? !L.visible : bi;
			bj = bi ? "show" : "hide";
			L.group[bj]();
			if (bh) {
				bh[bj]()
			}
			if (bf) {
				bf[bj]()
			}
			if (M) {
				M[bj]()
			}
			if (be) {
				be[bj]()
			}
			if (L.legendItem) {
				bg.legend.colorizeItem(L, bi)
			}
		},
		slice: function(be, bj, bh) {
			var L = this,
				bf = L.series,
				bg = bf.chart,
				M = L.slicedTranslation,
				bi;
			ay(bh, bg);
			bj = b(bj, true);
			be = L.sliced = j(be) ? be : !L.sliced;
			bi = {
				translateX: (be ? M[0] : bg.plotLeft),
				translateY: (be ? M[1] : bg.plotTop)
			};
			L.group.animate(bi);
			if (L.shadowGroup) {
				L.shadowGroup.animate(bi)
			}
		}
	});
	var l = aP(ap, {
		type: "pie",
		isCartesian: false,
		pointClass: Z,
		pointAttrToOptions: {
			stroke: "borderColor",
			"stroke-width": "borderWidth",
			fill: "color"
		},
		getColor: function() {
			this.initialColor = this.chart.counters.color
		},
		animate: function() {
			var L = this,
				M = L.data;
			each(M, function(bf) {
				var bh = bf.graphic,
					bg = bf.shapeArgs,
					be = -o / 2;
				if (bh) {
					bh.attr({
						r: 0,
						start: be,
						end: be
					});
					bh.animate({
						r: bg.r,
						start: bg.start,
						end: bg.end
					}, L.options.animation)
				}
			});
			L.animate = null
		},
		translate: function() {
			var bx = 0,
				bo = this,
				br = -0.25,
				bu = 1000,
				M = bo.options,
				bf = M.slicedOffset,
				bn = bf + M.borderWidth,
				bm = M.center.concat([M.size, M.innerSize || 0]),
				bq = bo.chart,
				bl = bq.plotWidth,
				bt = bq.plotHeight,
				bg, be, bv, bw = bo.data,
				bp = 2 * o,
				bj, bs = ad(bl, bt),
				L, bk, bi, bh = M.dataLabels.distance;
			bm = map(bm, function(bz, by) {
				L = /%$/.test(bz);
				return L ? [bl, bt, bs, bs][by] * p(bz) / 100 : bz
			});
			bo.getX = function(bz, by) {
				bv = W.asin((bz - bm[1]) / (bm[2] / 2 + bh));
				return bm[0] + (by ? -1 : 1) * (aU(bv) * (bm[2] / 2 + bh))
			};
			bo.center = bm;
			each(bw, function(by) {
				bx += by.y
			});
			each(bw, function(by) {
				bj = bx ? by.y / bx : 0;
				bg = u(br * bp * bu) / bu;
				br += bj;
				be = u(br * bp * bu) / bu;
				by.shapeType = "arc";
				by.shapeArgs = {
					x: bm[0],
					y: bm[1],
					r: bm[2] / 2,
					innerR: bm[3] / 2,
					start: bg,
					end: be
				};
				bv = (be + bg) / 2;
				by.slicedTranslation = map([aU(bv) * bf + bq.plotLeft, C(bv) * bf + bq.plotTop], u);
				bk = aU(bv) * bm[2] / 2;
				bi = C(bv) * bm[2] / 2;
				by.tooltipPos = [bm[0] + bk * 0.7, bm[1] + bi * 0.7];
				by.labelPos = [bm[0] + bk + aU(bv) * bh, bm[1] + bi + C(bv) * bh, bm[0] + bk + aU(bv) * bn, bm[1] + bi + C(bv) * bn, bm[0] + bk, bm[1] + bi, bh < 0 ? "center" : bv < bp / 4 ? "left" : "right", bv];
				by.percentage = bj * 100;
				by.total = bx
			});
			this.setTooltipPoints()
		},
		render: function() {
			var L = this;
			this.drawPoints();
			if (L.options.enableMouseTracking !== false) {
				L.drawTracker()
			}
			this.drawDataLabels();
			if (L.options.animation && L.animate) {
				L.animate()
			}
			L.isDirty = false
		},
		drawPoints: function() {
			var bg = this,
				bi = bg.chart,
				bh = bi.renderer,
				be, L, bk, bj = bg.options.shadow,
				M, bf;
			each(bg.data, function(bl) {
				L = bl.graphic;
				bf = bl.shapeArgs;
				bk = bl.group;
				M = bl.shadowGroup;
				if (bj && !M) {
					M = bl.shadowGroup = bh.g("shadow").attr({
						zIndex: 4
					}).add()
				}
				if (!bk) {
					bk = bl.group = bh.g("point").attr({
						zIndex: 5
					}).add()
				}
				be = bl.sliced ? bl.slicedTranslation : [bi.plotLeft, bi.plotTop];
				bk.translate(be[0], be[1]);
				if (M) {
					M.translate(be[0], be[1])
				}
				if (L) {
					L.animate(bf)
				} else {
					bl.graphic = bh.arc(bf).attr(aw(bl.pointAttr[Y], {
						"stroke-linejoin": "round"
					})).add(bl.group).shadow(bj, M)
				}
				if (bl.visible === false) {
					bl.setVisible(false)
				}
			})
		},
		drawDataLabels: function() {
			var bi = this,
				bK = bi.data,
				bA, bL = bi.chart,
				bx = bi.options.dataLabels,
				bf = b(bx.connectorPadding, 10),
				bv = b(bx.connectorWidth, 1),
				L, bH, bw = b(bx.softConnector, true),
				bo = bx.distance,
				bh = bi.center,
				bF = bh[2] / 2,
				M = bh[1],
				bm = bo > 0,
				bg, bn, bE, bz = [
					[],
					[]
				],
				bD, bC, bp, bk, bJ, bI = 2,
				bG;
			if (!bx.enabled) {
				return
			}
			ap.prototype.drawDataLabels.apply(bi);
			each(bK, function(bO) {
				if (bO.dataLabel) {
					bz[bO.labelPos[7] < o / 2 ? 0 : 1].push(bO)
				}
			});
			bz[1].reverse();
			bJ = function(bP, bO) {
				return bO.y - bP.y
			};
			bE = bz[0][0] && bz[0][0].dataLabel && p(bz[0][0].dataLabel.styles.lineHeight);
			while (bI--) {
				var bM = [],
					bj, bt = [],
					bB = bz[bI],
					by, br = bB.length,
					bN;
				for (by = M - bF - bo; by <= M + bF + bo; by += bE) {
					bM.push(by)
				}
				bj = bM.length;
				if (br > bj) {
					bk = [].concat(bB);
					bk.sort(bJ);
					bG = br;
					while (bG--) {
						bk[bG].rank = bG
					}
					bG = br;
					while (bG--) {
						if (bB[bG].rank >= bj) {
							bB.splice(bG, 1)
						}
					}
					br = bB.length
				}
				for (bG = 0; bG < br; bG++) {
					bA = bB[bG];
					bn = bA.labelPos;
					var bq = 9999,
						bu, bs;
					for (bs = 0; bs < bj; bs++) {
						bu = ba(bM[bs] - bn[1]);
						if (bu < bq) {
							bq = bu;
							bN = bs
						}
					}
					if (bN < bG && bM[bG] !== null) {
						bN = bG
					} else {
						if (bj < br - bG + bN && bM[bG] !== null) {
							bN = bj - br + bG;
							while (bM[bN] === null) {
								bN++
							}
						} else {
							while (bM[bN] === null) {
								bN++
							}
						}
					}
					bt.push({
						i: bN,
						y: bM[bN]
					});
					bM[bN] = null
				}
				bt.sort(bJ);
				for (bG = 0; bG < br; bG++) {
					bA = bB[bG];
					bn = bA.labelPos;
					bg = bA.dataLabel;
					var bl = bt.pop(),
						be = bn[1];
					bp = bA.visible === false ? at : al;
					bN = bl.i;
					bC = bl.y;
					if ((be > bC && bM[bN + 1] !== null) || (be < bC && bM[bN - 1] !== null)) {
						bC = be
					}
					bD = bi.getX(bN === 0 || bN === bM.length - 1 ? be : bC, bI);
					bg.attr({
						visibility: bp,
						align: bn[6]
					})[bg.moved ? "animate" : "attr"]({
						x: bD + bx.x + ({
							left: bf,
							right: -bf
						}[bn[6]] || 0),
						y: bC + bx.y
					});
					bg.moved = true;
					if (bm && bv) {
						L = bA.connector;
						bH = bw ? [au, bD + (bn[6] === "left" ? 5 : -5), bC, "C", bD, bC, 2 * bn[2] - bn[4], 2 * bn[3] - bn[5], bn[2], bn[3], av, bn[4], bn[5]] : [au, bD + (bn[6] === "left" ? 5 : -5), bC, av, bn[2], bn[3], av, bn[4], bn[5]];
						if (L) {
							L.animate({
								d: bH
							});
							L.attr("visibility", bp)
						} else {
							bA.connector = L = bi.chart.renderer.path(bH).attr({
								"stroke-width": bv,
								stroke: bx.connectorColor || bA.color || "#606060",
								visibility: bp,
								zIndex: 3
							}).translate(bL.plotLeft, bL.plotTop).add()
						}
					}
				}
			}
		},
		drawTracker: aL.prototype.drawTracker,
		getSymbol: function() {}
	});
	a6.pie = l;
	aD.Charts = {
		Chart: a3,
		dateFormat: aB,
		pathAnim: k,
		getOptions: E,
		hasRtlBug: aM,
		numberFormat: i,
		Point: g,
		Color: w,
		Renderer: aG,
		seriesTypes: a6,
		setOptions: aC,
		Series: ap,
		addEvent: addEvent,
		removeEvent: removeEvent,
		createElement: ar,
		discardElement: U,
		css: ax,
		each: each,
		extend: aw,
		map: map,
		merge: merge,
		pick: b,
		extendClass: aP
	}
}());
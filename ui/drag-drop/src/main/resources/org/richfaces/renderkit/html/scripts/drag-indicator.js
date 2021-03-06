DragIndicator = {

    init: function(event) {
    	var ieVersion = RichFaces.getIEVersion();
		DragIndicator.isIE6 = (ieVersion && ieVersion < 7);
		this.realParent = this.parentNode;
		this._nextSibling = this.nextSibling;
    },

    setContent: function(name, single, params) {
		Element.clearChildren(this);

		var p = DnD.getDnDDefaultParams(this);
		
		if (!p) {
			p = {};
		}

		if (params) {
			Object.extend(p, params);
		}
		
		if (!p['marker']) {
			if (p[name]) {
				p['marker'] = p[name];
			} else {
				p['marker'] = this.markers[name];
			}
		}

		var parts;

		if (single) {
			parts = this.indicatorTemplates['single'];
		} else {
			parts = this.indicatorTemplates['multi'];
		}
		
		new Insertion.Top(this, parts.invoke('getContent', p).join(''));
		
		this.adjustIFrame();
	},

	show: function() {
		if (!this.floatedToBody) {
			if (!this.realParent) {
				this.realParent = this.parentNode;
				this._nextSibling = this.nextSibling;
			}
			this.realParent.removeChild(this);

			document.body.appendChild(this);
			this.floatedToBody = true;
		}
		Element.show(this);
		this.style.position = 'absolute';
		
		this.adjustIFrame(true);
		if (this.iframe) {
			this.realParent.removeChild(this.iframe);
			document.body.appendChild(this.iframe);
			Element.show(this.iframe);
		}
	},

	hide: function() {
		Element.hide(this);
		this.style.position = '';
		this.offsets = undefined;
		this.leave();
		if (this.floatedToBody && this.realParent) {
			document.body.removeChild(this);
			
			if (this._nextSibling) {
				this.realParent.insertBefore(this, this._nextSibling);
			} else {
				this.realParent.appendChild(this);
			}

			this.floatedToBody = false;
		}
		
		this.adjustIFrame(false);
		if (this.iframe) {
			Element.hide(this.iframe);
			document.body.removeChild(this.iframe);
			this.realParent.appendChild(this.iframe);
		}
	},

	position: function(x, y) {
		if (!this.offsets) {
			Element.show(this);
			this.style.position = 'absolute';
		}		
		Element.setStyle(this, {"left": x + "px", "top": y + "px"});
		
		this.moveIFrame(x, y);
	},

	accept: function() {
		Element.removeClassName(this, 'drgind_default');
		Element.removeClassName(this, 'drgind_reject');
		Element.addClassName(this, 'drgind_accept');

		var acceptClass = this.getAcceptClass();
		if (acceptClass) {
			Element.addClassName(this, acceptClass);
		}
	},

	reject: function() {
		Element.removeClassName(this, 'drgind_default');
		Element.removeClassName(this, 'drgind_accept');
		Element.addClassName(this, 'drgind_reject');

		var rejectClass = this.getRejectClass();
		if (rejectClass) {
			Element.addClassName(this, rejectClass);
		}
	},

	leave: function() {
		Element.removeClassName(this, 'drgind_accept');
		Element.removeClassName(this, 'drgind_reject');
		Element.addClassName(this, 'drgind_default');

		var acceptClass = this.getAcceptClass();
		var rejectClass = this.getRejectClass();
		if (acceptClass) {
			Element.removeClassName(this, acceptClass);
		}
		if (rejectClass) {
			Element.removeClassName(this, rejectClass);
		}
	},

	getAcceptClass: function() {
		return this.ils_acceptClass;
	},

	getRejectClass: function() {
		return this.ils_rejectClass;
	},
	initIFrame: function() {
		if (DragIndicator.isIE6 && !this.iframe) {
			var fakeElement = $(document.createElement("div"));
			fakeElement.innerHTML = '<iframe class=\"rich-dragindicator-iframe\" src="" scrolling="no" frameborder="0" style="filter:Alpha(opacity=0);position:absolute;top:0px;left:0px;display:block"></iframe>';
			this.iframe = $(fakeElement.getElementsByTagName("iframe")[0]);
			fakeElement.removeChild(this.iframe);
			this.realParent.appendChild(this.iframe);
		}
	},
	moveIFrame: function(x, y) {
		this.initIFrame();
		if(this.iframe) {
			Element.setStyle(this.iframe, {"left": x, "top": y});
		}
	},
	adjustIFrame: function(show) {
		this.initIFrame();
		
		if(this.iframe)
		{
			if (Element.visible(this)) {
				var w = this.offsetWidth + "px";
				var h = this.offsetHeight + "px";
				var x = this.offsetLeft + "px"; 
				var y = this.offsetTop + "px";

				Element.setStyle(this.iframe, {"left": x, "top": y});
				Element.setStyle(this.iframe, {"width": w, "height": h});
			}
			
			if (arguments.length > 0) {
				if (show) {
					this.iframe.style.display = "";
				} else {
					this.iframe.style.display = "none";
				}
			}
		}
	}	
};

function createDragIndicator(elt, acceptClass, rejectClass) {
    Object.extend(elt, DragIndicator);
    elt.init();
	
	elt.ils_acceptClass = acceptClass;
	elt.ils_rejectClass = rejectClass;
}

//	<table border="0" cellpadding="3" cellspacing="0" class="drgind_internal" style="height : 100%">
//		<tr>
//			<td class="drgind_marker">
//			{marker}
//			</td>
//			<td class="drgind_text">
//				<div class="drgind_wordcut drgind_text_content">
//				{label}
//				</div>
//			</td>
//		</tr>
//	</table>
//	<div class="drgind_shadow" />
DefaultDragIndicatorView = 
[
	new E('table', 
			{
				'style':'height : 100%; z-index: 2;', 
				'className':'drgind_internal', 
				'cellspacing':'0', 
				'cellpadding':'3', 
				'border':'0'
			},
			[
				new E('tbody',{},
					[
						new E('tr',{},
							[
								new E('td',
										{
											'className':'drgind_marker'
										},
										[
											new ET(function (context) { return Richfaces.eval("marker", context)})
										]
								),
								new E('td',
										{
											'className':'drgind_text'
										},
										[
											new E('div',
													{
														'className':'drgind_wordcut drgind_text_content'
													},
													[
														new ET(function (context) { return Richfaces.eval("label", context)})
													]
											)
										]
								)
							]
						)
					]
				)
			]
	)
];

import {MathjaxAdapter} from "./mathjax-adapter";
import {hoverAreaAroundElements} from "./mathjax-style-hacks";

class MathjaxUnification extends MathjaxAdapter {
	connectedCallback() {
		super.connectedCallback();
		this.requestTypeset(null);
	}

	static get properties() {
		return {
			content: {type: String}
		}
	}

	protected showStep(_n: number): void {}

	protected calculateSteps(_extraData: any) {
		const root = this.shadowRoot!;
		// re-apply style hacks
		if (!root.querySelector("#style-fixes")) {
			const style = document.createElement('style');
			style.innerHTML = hoverAreaAroundElements;
			style.id = "style-fixes";
			root.querySelector("mjx-head")!.appendChild(style);
		}
		// re-attach event listeners
		const prooftree = this.shadowRoot!.host.ownerDocument.querySelector("tc-proof-tree")!;
		root.querySelector("mjx-body")!.addEventListener("mouseover", e => {
			// @ts-ignore
			prooftree.handleHoverEvent(e as MouseEvent, true);
		});
		root.querySelector("mjx-body")!.addEventListener("mouseout", e => {
			// @ts-ignore
			prooftree.handleHoverEvent(e as MouseEvent, false);
		});
	}

	protected showLatex(code: string): void {
		let el = this.shadowRoot!.getElementById("tc-content");
		if (!el) {
			setTimeout(() => this.showLatex(code), 50);
			return;
		}
		el.innerHTML = code;
		this.requestTypeset(null);
	}
}

customElements.define('tc-unification', MathjaxUnification);

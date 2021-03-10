import {MathjaxAdapter} from "./mathjax-adapter";

class MathjaxUnification extends MathjaxAdapter {
	connectedCallback() {
		super.connectedCallback();
		this.requestTypeset();
	}

	static get properties() {
		return {
			content: {type: String}
		}
	}

	protected showStep(_n: number): void {
		this.requestTypeset();
	}
}

customElements.define('tc-unification', MathjaxUnification);

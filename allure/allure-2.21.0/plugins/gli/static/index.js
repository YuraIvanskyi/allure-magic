
const template = `
<h2 class="widget__title"><%= title %></h2>
{{#if items}}
<div class="table  table_hover widget__table">
    {{#each items}}
    <div class="{{b " table" "row" }}" disabled>
        <div class="{{b " table" "col" }} long-line">
            {{name}}
        </div>
        <div class="{{b " table" "col" }} long-line">
            {{#each value}}
            {{linky this}}{{#unless @last}},<br>{{/unless}}
            {{/each}}
        </div>
    </div>
    {{/each}}
    {{#if overLimit}}
    <a class="{{b " table" "row" }} clickable environment-widget__expand">
        <div class="{{b " table" "col" center=true}}">{{t 'widget.globalInfo.showAll'}}</div>
    </a>
    {{/if}}
</div>

{{else}}
<div class="widget__noitems">{{t 'widget.globalInfo.empty'}}</div>
{{/if}}
`

allure.api.addTranslation('en', {
    widget: {
        globalInfo: {
            name: 'Global Info',
            showAll: 'Show All',
            emty: 'Empty'
        }
    }
});

allure.api.addWidget('widgets', 'global-info', Backbone.Marionette.View.extend({
    template(data) {
        console.log(data);
        console.log(this);
        final = Backbone.Marionette.Renderer.render(template, data);
        console.log(final);
        return final
    },

    // @on("click .environment-widget__expand")
    // onExpandClick() {
    //     this.listLimit = this.model.get("items").length;
    //     this.render();
    // },
    onRender: function () {
        console.log(this);
    },
    initialize() {
        this.listLimit = 15;
    },
    serializeData() {
        const items = this.model.get("items");
        return {
            items: items.slice(0, this.listLimit),
            overLimit: items.length > this.listLimit,
            title: 'Global Info'
        };
    }
}));
class WidgetTemplate {

    constructor(data) {
        this.content = data;
    }

    buildEntries() {
        let entries = ``
        for (const entry of this.content.items) {
            switch (entry.decoration) {
                case 'link':
                    entries += this.entryBase(
                        this.stack(
                            this.entryName(entry.name),
                            this.linkEntryValue(entry.display_value, entry.value)
                        )
                    );
                    break;
                case 'code':
                    entries += this.entryBase(
                        this.stack(
                            this.entryName(entry.name),
                            this.codeEntryValue(entry.value)
                        )
                    );
                    break;
                default:
                    entries += this.entryBase(
                        this.stack(
                            this.entryName(entry.name),
                            this.entryValue(entry.value)
                        )
                    );
            }
        }
        if (this.content.overLimit) {
            entries += this.entryMore(this.content.showAll);
        }
        return entries;

    }

    build() {
        return this.stack(
            this.title(this.content.title, this.content.subtitle),
            this.bodyTable(
                this.optional(
                    this.buildEntries(),
                    this.entryNoValues('No messages, warnings or gloabl errors'),
                    this.content.items.length
                )
            ),
        )
    }

    stack = (...args) => args.join('')
    optional = (value, alternative, condition = true) => condition ? value : alternative
    bodyTable = (innerHtml) => `<div class="table table_hover widget__table">${innerHtml}</div >`
    title = (title, subtitle) => subtitle ?
        `<h2 class="widget__title" >${title} <span class="widget__subtitle"> ${subtitle}</span></h2>`
        : `<h2 class="widget__title" >${title}</h2>`
    entryBase = (entryHtml) => `<div class="table__row" disabled>${entryHtml}</div>`
    entryMore = (showMoreText) => `
        <a class="table__row clickable">
            <div class="table__col table__col_center globalinfo-widget__expand">
            ${showMoreText}
            </div>
        </a>`
    entryNoValues = (noContentText) => `
        <div class="table__row">
            <div class="table__col table__col_center">
                ${noContentText}
            </div>
        </div>`
    entryName = (name) => `<div class="table__col long-line shorter_col">${name}</div>`
    entryValue = (value) => `<div class="table__col long-line">${value}</div>`
    linkEntryValue = (text, link) => `
        <div class="table__col long-line">
            <a class="link" href="${link}">${text} <i class="fa fa-external-link" aria-hidden="true"></i></a>
        </div>`
    codeEntryValue = (value) => `<div class="table__col long-line"><pre class="code"><code>${value}</code></pre></div>`
}

class GlobalInfoWidget extends Backbone.Marionette.View {
    events() {
        return { "click .globalinfo-widget__expand": "onExpandClick" }
    }
    template(data) {
        return new WidgetTemplate(data).build();
    }
    render() {
        super.render();
    }
    initialize() {
        this.listLimit = 5;
    }
    onExpandClick() {
        this.listLimit = this.model.get("items").length;
        this.render();
    }
    serializeData() {
        const items = this.model.get("items");
        return {
            items: items.slice(0, this.listLimit),
            overLimit: items.length > this.listLimit,
            title: 'QA Info & Warnings',
            showAll: 'Show All',
            subtitle: `${items.length} items total`
        };
    }
}
allure.api.addWidget('widgets', 'global-info', GlobalInfoWidget);
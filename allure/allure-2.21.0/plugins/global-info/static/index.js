class WidgetTemplate {

    constructor(data) {
        this.content = data;
        this.template = ``;
    }

    buildEntries() {
        entries = ``
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
        return entries;

    }

    build() {
        return this.stack(
            this.title(this.content.title, this.content.subtitle),
            this.bodyTable(
                this.optional(
                    this.buildEntries(),
                    this.entryNoValues('No messages, warnings or gloabl errors'),
                    this.content.items
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
            <div class="table__col table__col_center">
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
    entryValue = (value) => `<div class="table__col long-line shorter_col">${value}</div>`
    linkEntryValue = (text, link) => `<a class="link" href="${link}">${text}</a>`
    codeEntryValue = (value) => `<pre class="code"><code>${value}<code></pre>`
}

class GlobalInfoWidget extends Backbone.Marionette.View {

    template(data) {
        return new WidgetTemplate(data).build();
    }
    initialize() {
        this.listLimit = 10;
    }
    serializeData() {
        const items = this.model.get("items");
        return {
            items: items.slice(0, this.listLimit),
            overLimit: items.length > this.listLimit,
            title: 'QA Info & Warnings',
            subtitle: `${items.length} items total`
        };
    }
}
allure.api.addWidget('widgets', 'global-info', GlobalInfoWidget);
allure.api.addTranslation('en', {
    widget: {
        globalInfo: {
            name: 'Global Info',
            showAll: 'Show All',
            empty: 'Empty'
        }
    }
});
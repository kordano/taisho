(ns taisho-client.core
  (:require [goog.dom :as gdom]
            [kioo.om :refer [content set-attr do-> substitute listen]]
            [kioo.core :refer [handle-wrapper]]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom])
    (:require-macros [kioo.om :refer [defsnippet deftemplate]]))

(enable-console-print!)

(defonce app-state (atom {:text "Hello world!"}))

(defsnippet status-card "templates/trello_lists.html" [:.status-card]
  [{:keys [counter title]}]
  {[:#counter] (content counter)
   [:#title] (content title)})

(defui HelloWorld
  Object
  (render [this]
    (dom/div
     nil
     (dom/h3 #js {:className "center-align"} "Project Status")
     (dom/div
      #js {:className "row"}
      (mapv
       status-card
       [{:title "open tasks" :counter 42}
        {:title "weekly tasks" :counter 23}
        {:title "closed tasks" :counter 666}]))))) 

(def hello (om/factory HelloWorld))

(js/ReactDOM.render (hello) (gdom/getElement "app"))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)

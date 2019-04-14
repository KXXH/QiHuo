"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.addObserveTarget = addObserveTarget;
exports.removeObserveTarget = removeObserveTarget;
exports.getTargetRect = getTargetRect;

var _addEventListener = _interopRequireDefault(require("rc-util/lib/Dom/addEventListener"));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

// ======================== Observer ========================
var TRIGGER_EVENTS = ['resize', 'scroll', 'touchstart', 'touchmove', 'touchend', 'pageshow', 'load'];
var observerEntities = [];

function addObserveTarget(target, affix) {
  if (!target) return;
  var entity = observerEntities.find(function (item) {
    return item.target === target;
  });

  if (entity) {
    entity.affixList.push(affix);
  } else {
    entity = {
      target: target,
      affixList: [affix],
      eventHandlers: {}
    };
    observerEntities.push(entity); // Add listener

    TRIGGER_EVENTS.forEach(function (eventName) {
      entity.eventHandlers[eventName] = (0, _addEventListener["default"])(target, eventName, function (event) {
        entity.affixList.forEach(function (affix) {
          affix.updatePosition(event);
        });
      });
    });
  }
}

function removeObserveTarget(affix) {
  var observerEntity = observerEntities.find(function (oriObserverEntity) {
    var hasAffix = oriObserverEntity.affixList.some(function (item) {
      return item === affix;
    });

    if (hasAffix) {
      oriObserverEntity.affixList = oriObserverEntity.affixList.filter(function (item) {
        return item !== affix;
      });
    }

    return hasAffix;
  });

  if (observerEntity && observerEntity.affixList.length === 0) {
    observerEntities = observerEntities.filter(function (item) {
      return item !== observerEntity;
    }); // Remove listener

    TRIGGER_EVENTS.forEach(function (eventName) {
      var handler = observerEntity.eventHandlers[eventName];

      if (handler && handler.remove) {
        handler.remove();
      }
    });
  }
}

function getTargetRect(target) {
  return target !== window ? target.getBoundingClientRect() : {
    top: 0,
    bottom: window.innerHeight
  };
}